package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    
    public Account create(String accountType, String name, String bank);
    
    public Account find(String name);
    
    public String debit(long id,long debiteras);
    
    public String kredit(long id,long krediteras);
}
