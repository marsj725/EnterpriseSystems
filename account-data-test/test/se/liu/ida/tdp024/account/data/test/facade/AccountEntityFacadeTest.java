package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    
    private StorageFacade storageFacade = new StorageFacadeDB();
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
        Account konto = accountEntityFacade.create(accountType, name, bank);
        
        Account account = accountEntityFacade.find(name);
        System.out.println(konto.getId()+" :: "+account.getId());
        Assert.assertEquals(konto.getId(), account.getId());
        Assert.assertEquals(accountType, account.getAccountType());
        Assert.assertEquals(konto.getBankKey(), account.getBankKey());
    }
    
    @Test
    public void testDebitandKredit() {
        
        String accountType = "SAVINGS";
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        Boolean debit;
        Boolean kredit;
        
        Account konto = accountEntityFacade.create(accountType, name, bank);
        
        debit = accountEntityFacade.debit(konto.getId(), 10);
        Assert.assertEquals(false, debit);
        
        kredit = accountEntityFacade.kredit(konto.getId(), 10);
        Assert.assertEquals(true, kredit);
        
        debit = accountEntityFacade.debit(konto.getId(), 10);
        Assert.assertEquals(true, debit);
        
        debit = accountEntityFacade.debit(konto.getId(), 10);
        Assert.assertEquals(false, debit);
    }
}