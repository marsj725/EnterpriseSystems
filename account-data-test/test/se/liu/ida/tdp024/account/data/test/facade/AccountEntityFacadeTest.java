package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.Testfacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private StorageFacade storageFacade = new StorageFacadeDB();
    private AccountEntityFacade accountEntityFacade = new Testfacade();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        
        String accountType = "SAVINGS";
        String name = "Martin Sjödin Jonsson";
        String bank = "SWEDBANK";
        long id = accountEntityFacade.create(accountType, name, bank);
        
        Account account = accountEntityFacade.find(name);
        System.out.println(id+" :: "+account.getId());
        Assert.assertEquals(id, account.getId());

        //Assert.assertEquals(accountType, account.getAccountType());
    }
}