package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.User;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.UserType;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();  
    private AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(),new TransactionEntityFacadeDB());
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    public AccountLogicFacadeTest(){
        System.out.println("hej");
    }
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreateFind() {
        
        String accountType = "SAVINGS";
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        String konto = accountLogicFacade.create(accountType, name, bank);
        List<Account> account = accountLogicFacade.find(name);
        
        String nameBankKey = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
        User bankUser = jsonSerializer.fromJson(nameBankKey, UserType.class);  

        Assert.assertEquals("OK", konto);
        Assert.assertEquals(accountType, account.get(0).getAccountType());
    }
    
    @Test
    public void testDebitKredit(){       
        String accountType = "SAVINGS";
        String name = "Marcus Bendtsen";
        String bank = "SWEDBANK";
        Boolean debit;
        Boolean kredit;
        
        String konto = accountLogicFacade.create(accountType, name, bank);
        List<Account> account = accountLogicFacade.find(name);
        //System.out.println("Konto: "+konto.getId())
        
        
        debit = accountLogicFacade.debit(account.get(0).getId(), 10);
        Assert.assertEquals(false, debit);
        
        kredit = accountLogicFacade.credit(account.get(0).getId(), 10);
        Assert.assertEquals(true, kredit);
        
        debit = accountLogicFacade.debit(account.get(0).getId(), 10);
        Assert.assertEquals(true, debit);
        
        kredit = accountLogicFacade.credit(account.get(0).getId(), 10);
        Assert.assertEquals(true, kredit);
        
        kredit = accountLogicFacade.credit(account.get(0).getId(), 10);
        Assert.assertEquals(true, kredit);
        

        
        debit = accountLogicFacade.debit(account.get(0).getId(), 30);
        Assert.assertEquals(false, debit);
    }
}