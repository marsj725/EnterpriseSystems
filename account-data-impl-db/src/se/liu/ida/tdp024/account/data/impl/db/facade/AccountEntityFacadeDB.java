package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();

    //Account logger
    
    /**
     *
     * @param accountType
     * @param name
     * @param bank
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    private long transactionId;
    
    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;
        
    @Override
    public String create(String accountType, String name, String bank){
        EntityManager em = EMF.getEntityManager();
        String personalKey = null;
        int holdings;
        
        try{
            em.getTransaction().begin();
            Account account = new AccountDB();
            
            String bankResponse = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name);         
            
            Account user = jsonSerializer.fromJson(userResponse, Account.class);
            Account banken = jsonSerializer.fromJson(bankResponse, Account.class);
            
            account.setAccountId(accountId);
            account.setBankKey(banken.getBankKey());
            account.setHoldings(0);
            account.setPersonalKey(user.getPersonalKey());
            account.setAccountType(accountType);
            
            em.persist(account);
            em.getTransaction().commit();
            return "OK";
            
        }catch (Exception e){
            return "FAILED";
        }finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }
    }

    @Override
    public String find(String name) {
        EntityManager em = EMF.getEntityManager();
        String response = null;
        Account temp = null;
        try{
            temp = em.find(Account.class, name);
            AccountJsonSerializer JSONConv = new AccountJsonSerializerImpl();
            response = JSONConv.toJson(temp);
            return response;
        }catch(Exception e){
            return "[]";
        }finally{
            em.close();
        }
    }
    @Override
    public String debit(int id, int debiteras) {
        EntityManager em = EMF.getEntityManager();
        String response = null;
        Account temp = null;
        int tempHoldings = 0;
        try{
            em.getTransaction().begin();
            temp = em.find(Account.class, id);
            tempHoldings = temp.getHoldings()-debiteras;
            temp.setHoldings(tempHoldings);
            em.merge(temp);
            em.getTransaction().commit();
            return "OK";
        }catch(Exception e){
            return "FAILED";
        }finally{
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public String kredit(int id, int krediteras) {
        EntityManager em = EMF.getEntityManager();
        String response = null;
        Account temp = null;
        int tempHoldings = 0;
        try{
            em.getTransaction().begin();
            temp = em.find(Account.class, id);
            tempHoldings = temp.getHoldings()+krediteras;
            temp.setHoldings(tempHoldings);
            
            Transaction tempTransaction = null;
            tempTransaction.setAmount(krediteras);
            tempTransaction.setTransactionId(transactionId);
            
            temp.addTransaction(tempTransaction);
            em.merge(temp);
            em.getTransaction().commit();
            return "OK";
        }catch(Exception e){
            return "FAILED";
        }finally{
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public List<Transaction> transactions() {
        EntityManager em = EMF.getEntityManager();
        Account tempAccount = null;
        try{
       
            tempAccount = em.find(Account.class, id);

            return tempAccount.getTransactions();
        }catch(Exception e){
            return null;
        }finally{
            em.close();
        }
    }
}
