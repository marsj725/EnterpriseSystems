package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {

    public Transaction create(Account account,String type, long amount, String status);
    
    public List<Transaction> find(long id); 
}
