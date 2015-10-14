package tdp024.account.rest.service;

import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountServiceTest {

    //-- Units under test ---//
    private StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();

    }

    @Test
    public void testCreate() {
        
    }
}