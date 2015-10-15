/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

/**
 *
 * @author masj
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    @Override
    public Transaction create(Account account, String type, long amount, String status) {
        
        EntityManager em = EMF.getEntityManager();
        Transaction transaction = new TransactionDB();
        int holdings;   
        try{
            String datum = new Date().toString();
            em.getTransaction().begin();
            transaction.setAccount(account);
            transaction.setAmount(amount);
            transaction.setCreated(datum);
            transaction.setType(type);
            transaction.setStatus(status);

            em.persist(transaction);
            em.getTransaction().commit();
            
            return transaction;
        }catch (Exception e){
            System.out.println("ERROR OCCURED!");
            return transaction;
        }finally{
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<Transaction> find(long id) {
       EntityManager em = EMF.getEntityManager();
       String response = null;
       List<Transaction> returnList = new ArrayList();
       try{
            Query query = em.createQuery("SELECT c FROM TransactionDB c WHERE c.account.id = :id ");
            query.setParameter("id", id);
            returnList = query.getResultList();
           return returnList;
        }catch(Exception e){
            return returnList;
        }finally{
            em.close();
        }
    }

    
}
