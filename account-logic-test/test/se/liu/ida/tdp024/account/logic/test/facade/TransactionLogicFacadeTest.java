/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.entity.User;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.UserType;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

/**
 *
 * @author marsj725
 */
public class TransactionLogicFacadeTest {
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();  
    private AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(),new TransactionEntityFacadeDB());
    private TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    public TransactionLogicFacadeTest(){
        System.out.println("TransactionLogic!");
    }
    
    @Test
    public void testCreateFind() {
        
        String accountType = "SAVINGS";
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        Boolean credit;
        long amount = 0;
        
        String konto = accountLogicFacade.create(accountType, name, bank);
        List<Account> account = accountLogicFacade.find(name); 
        
        credit = accountLogicFacade.credit(account.get(0).getId(), 10);
        credit = accountLogicFacade.credit(account.get(0).getId(), 10);
        credit = accountLogicFacade.credit(account.get(0).getId(), 10);
        List<Transaction> transaction = transactionLogicFacade.find(account.get(0).getId());
        
        
        Assert.assertEquals(10, transaction.get(0).getAmount());
        Assert.assertEquals(10, transaction.get(1).getAmount());
        Assert.assertEquals(10, transaction.get(1).getAmount());
        
    }
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
}
