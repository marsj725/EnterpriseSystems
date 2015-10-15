package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {

    public Transaction create(long accountid, String type, long amount, String status);
    
    public List<Transaction> find(long id); 
}
