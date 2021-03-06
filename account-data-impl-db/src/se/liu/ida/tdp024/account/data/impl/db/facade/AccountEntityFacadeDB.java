/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

/**
 *
 * @author marsj725
 */
public class AccountEntityFacadeDB implements AccountEntityFacade {
 

    @Override
    public Account create(String accountType, String nameKey, String bankKey){

        EntityManager em = EMF.getEntityManager();
        String personalKey = null;
        int holdings;
        Account account = new AccountDB();
        try{
            em.getTransaction().begin();
            account.setHoldings(0);
            account.setAccountType(accountType);
            account.setPersonKey(nameKey);
            account.setBankKey(bankKey);
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
    public List<Account> find(String nameKey) {
       EntityManager em = EMF.getEntityManager();
       List accountList = new ArrayList();
       String response = null;
       Account temp = new AccountDB();
       try{
           Query query = em.createQuery("SELECT c FROM AccountDB c WHERE c.personKey = :personKey ");
           query.setParameter("personKey", nameKey);
           accountList = query.getResultList();
           return accountList;
        }catch(Exception e){
            return accountList;
        }finally{
            em.close();
        }
    }
    @Override
    public Boolean debit(long id, long debiteras) {
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
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }finally{
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Boolean kredit(long id, long krediteras) {
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
            return true;
        }catch(Exception e){
            return false;
        }finally{
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Account findAccountByID(long id) {
        EntityManager em = EMF.getEntityManager();
        Account konto = null;
        try{
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM AccountDB c WHERE c.id = :id ");
            query.setParameter("id", id);
            konto = (Account) query.getSingleResult();
            return konto;
        }catch(Exception e){
            return konto;
        }
    }
}
