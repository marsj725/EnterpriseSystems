/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
    //---- Unit under test ----//

/**
 *
 * @author marsj725
 */
public class TransactionEntityFacadeTest {
    private StorageFacade storageFacade = new StorageFacadeDB();
    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    @Test
    public void testCreate() {
        
        String accountType = "SAVINGS";
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        String debit;
        String kredit;
        String status = "OK";
        String type = "DEBIT";
        
        //Account konto = accountEntityFacade.create(accountType, name, bank);
        //System.out.println("KEY:"+konto.getId());
        
        Transaction transaction = transactionEntityFacade.create(1, type, 0, status);
        
        System.out.println("TRANSACTION_KEY:"+transaction.getId());
        
        Transaction result = transactionEntityFacade.find(transaction.getId());
        
        Assert.assertEquals(transaction.getId(), result.getId());
        Assert.assertEquals(0, result.getAmount());
        Assert.assertEquals(status, result.getStatus());
        System.out.println(transaction.getId() + " : "+result.getId());
        
    }
}
