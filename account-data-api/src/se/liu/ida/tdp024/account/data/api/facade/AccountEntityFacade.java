package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    
    public Account create(String accountType, String name, String bank);
    
    public List<Account> find(String name);
    
    public Boolean debit(long id,long debiteras);
    
    public Boolean kredit(long id,long krediteras);
}
