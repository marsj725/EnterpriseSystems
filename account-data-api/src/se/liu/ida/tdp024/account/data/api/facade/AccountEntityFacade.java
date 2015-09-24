package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountEntityFacade {
    
    public String create(String accountType, String name, String bank);
    
    public String find(String name);
    
    public String debit(int id,int debiteras);
    
    public String kredit(int id,int krediteras);
    
    public List<Transaction> transactions();
}
