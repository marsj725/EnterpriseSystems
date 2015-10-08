/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.entity.User;
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
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();   

    
    //AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl() {};
    //HTTPHelper httpHelper = new HTTPHelperImpl();
    @Override
    public Account create(String accountType, String name, String bank){

        EntityManager em = EMF.getEntityManager();
        String personalKey = null;
        int holdings;
            
            
        //Account accountDTos = jsonSerializer.fromJson(userResponse, AccountDB.class);
        
        
        try{
            em.getTransaction().begin();
            Account account = new AccountDB();
            account.setHoldings(0);
            account.setAccountType(accountType);
            
            String bankResponse = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name);     
            User user = jsonSerializer.fromJson(userResponse, UserType.class);
            User banks = jsonSerializer.fromJson(bankResponse, UserType.class);
            account.setPersonalKey(user.getKey());
            account.setBankKey(banks.getKey());
            
            em.persist(account);
            em.getTransaction().commit();
            
            return account;
        }catch (Exception e){
            Account account = new AccountDB();
            account.setId(999);
            return account;
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
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name);     
            User user = jsonSerializer.fromJson(userResponse, UserType.class);
            
            Query query = em.createQuery("SELECT c FROM AccountDB c WHERE c.personalKey = :personalKey ");
            query.setParameter("personalKey", user.getKey());

           return (Account)query.getSingleResult();
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
    
}
