package se.liu.ida.tdp024.account.logic.api.facade;


public interface AccountLogicFacade {
    
    public void create(long id, long amount);
    
    public void find(String name);
    
    public String debit(long value);
    
    public String kredit(long value);
    
}
