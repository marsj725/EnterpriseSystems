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
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    private long transactionId;
    
    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;
        
    @Override
    public long create(String accountType, String name, String bank){
        EntityManager em = EMF.getEntityManager();
        String personalKey = null;
        int holdings;
        Account account = new AccountDB();
        
        try{
            em.getTransaction().begin();
            
            String bankResponse = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name);         
            
            Account user = jsonSerializer.fromJson(userResponse, Account.class);
            Account banken = jsonSerializer.fromJson(bankResponse, Account.class);
            
            account.setId(accountId);
            account.setBankKey(banken.getBankKey());
            account.setHoldings(0);
            account.setPersonalKey(user.getPersonalKey());
            account.setAccountType(accountType);
            
            em.persist(account);
            em.getTransaction().commit();
            return accountId;
            
            
        }catch (Exception e){
            return 0;
        }finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }
    }

    @Override
    public Account find(String name) {
        EntityManager em = EMF.getEntityManager();
        String response = null;
        Account temp = null;
        try{
            temp = em.find(Account.class, name);
            AccountJsonSerializer JSONConv = new AccountJsonSerializerImpl();
            response = JSONConv.toJson(temp);
            return temp;
        }catch(Exception e){
            return null;
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
}
