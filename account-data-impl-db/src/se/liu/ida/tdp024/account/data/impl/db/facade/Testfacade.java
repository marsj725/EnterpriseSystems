/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import java.util.ServiceConfigurationError;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

/**
 *
 * @author marsj725
 */
public class Testfacade implements AccountEntityFacade {
    

    
    //AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    HTTPHelper httpHelper = new HTTPHelperImpl();
    @Override
    public long create(String accountType, String name, String bank){

        EntityManager em = EMF.getEntityManager();
        String personalKey = null;
        int holdings;
        
        
        try{
            em.getTransaction().begin();
            Account account = new AccountDB();
            account.setHoldings(0);
            account.setAccountType(accountType);
            
           // String bankResponse = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
           // String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name);         
            
           // Account user = jsonSerializer.fromJson(userResponse, Account.class);
           // Account banken = jsonSerializer.fromJson(bankResponse, Account.class);
            
            
           // account.setBankKey(banken.getBankKey());
            
           // account.setPersonalKey(user.getPersonalKey());
            
            
            //em.persist(account);
            //em.getTransaction().commit();
            //em.persist(account);
            em.getTransaction().commit();
            System.out.println(account.getId());
            return account.getId();
        }catch (Exception e){
            //throw new ServiceConfigurationError("Commit fails!");
            Account account = new AccountDB();
            account.setId(666);
            return account.getId();
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
       Account temp = new AccountDB();
       try{
            temp = em.find(AccountDB.class, name);

            
            //AccountJsonSerializer JSONConv = new AccountJsonSerializerImpl();
            //response = JSONConv.toJson(temp);
            return temp;
        }catch(Exception e){
            temp.setId(999);
            return temp;
        }finally{
            em.close();
        }
    }

    @Override
    public String debit(int id, int debiteras) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String kredit(int id, int krediteras) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> transactions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
