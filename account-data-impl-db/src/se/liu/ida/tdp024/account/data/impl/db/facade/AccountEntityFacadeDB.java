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
public class AccountEntityFacadeDB implements AccountEntityFacade {
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();   

    @Override
    public Account create(String accountType, String name, String bank){

        EntityManager em = EMF.getEntityManager();
        String personalKey = null;
        int holdings;
        Account account = new AccountDB();
        try{
            em.getTransaction().begin();

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

    public String debit(long id, long debiteras) {
        EntityManager em = EMF.getEntityManager();
        long tempHoldings = 0;
        try{
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM AccountDB c WHERE c.id = :id ");
            query.setParameter("id", id);
            Account konto = (Account) query.getSingleResult();
            
            if((konto.getHoldings() - debiteras) >= 0){
                tempHoldings = konto.getHoldings()-debiteras;
                konto.setHoldings(tempHoldings);
                em.merge(konto);
                em.getTransaction().commit();
                return "OK";
            }else{
                return "FAILED";
            }
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
    public String kredit(long id, long krediteras) {
        EntityManager em = EMF.getEntityManager();
        String response = null;
        Account temp = null;
        long tempHoldings = 0;
        try{
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM AccountDB c WHERE c.id = :id ");
            query.setParameter("id", id);
            
            Account konto = (Account) query.getSingleResult();
            konto.setHoldings((konto.getHoldings()+krediteras));

            em.merge(konto);
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
