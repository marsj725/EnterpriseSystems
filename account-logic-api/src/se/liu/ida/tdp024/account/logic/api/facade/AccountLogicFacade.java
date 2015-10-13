package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;


public interface AccountLogicFacade {
    
    public String create(String accountType, String name, String bank);
    
    public Account find(String name);
    
    public Boolean debit(String name, long debit);
    
    public Boolean kredit(String name,long value);
    
}
