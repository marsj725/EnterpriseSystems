package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;


public interface AccountLogicFacade {
    
    public String create(String accountType, String name, String bank);
    
    public List<Account> find(String name);
    
    public Boolean debit(long id, long debit);
    
    public Boolean credit(long id,long credit);
    
    public List<Transaction> findTransaction(long id);
}
