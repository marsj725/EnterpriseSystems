package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;


public interface AccountLogicFacade {
    
    public void create(String accountType, String name,String bank);
    
    public Account find(String name);
    
    public String debit(long value);
    
    public String kredit(long id,long value);
    
}
