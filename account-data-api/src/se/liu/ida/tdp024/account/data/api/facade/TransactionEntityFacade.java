package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionEntityFacade {

    public Transaction create(long accountid, String type, long amount, String status);
    
    public Transaction find(long id); 
}
