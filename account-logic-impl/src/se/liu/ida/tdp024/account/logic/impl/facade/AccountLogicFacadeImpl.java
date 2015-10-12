package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private TransactionEntityFacade transactionEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
    public String debit(int id, int amount){
        try{
        String status = accountEntityFacade.debit(id, amount);
        transactionEntityFacade.create(amount, status, amount, status);
        return status;
            
        }catch(Exception e){
            return "FAILED";
        }
    }
   
    @Override
    public Account find(String name) {
        return accountEntityFacade.find(name);
    }

    @Override
    public void create(String accountType, String name, String bank) {
        Account konto = accountEntityFacade.create(accountType, name, bank);
    }

    @Override
    public String kredit(long id,long value) {
        String accountStatus = accountEntityFacade.kredit(id, value);
        String transactionStatus = transactionEntityFacade.create(id, , value, );
        
        return "OK";
    }
}
