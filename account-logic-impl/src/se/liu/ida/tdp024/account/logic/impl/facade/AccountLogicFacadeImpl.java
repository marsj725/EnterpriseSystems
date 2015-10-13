package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.User;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.UserType;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();  
    private AccountEntityFacade accountEntityFacade;
    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade){
        this.accountEntityFacade = accountEntityFacade;
    }
       
    @Override
    public Account find(String name) {
        try{
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name);     
            User user = jsonSerializer.fromJson(userResponse, UserType.class);
            return accountEntityFacade.find(user.getKey());
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public String create(String accountType, String name, String bank) {
        String bankResponse = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
        String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name); 
        User user = jsonSerializer.fromJson(userResponse, UserType.class);
        User banks = jsonSerializer.fromJson(bankResponse, UserType.class);
        Account konto = accountEntityFacade.create(accountType, user.getKey(), banks.getKey());
        if(konto != null){
            return "OK";
        }else{
            return "FAILED";
        }
    }

    @Override
    public Boolean kredit(long id,long value) {
        String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name); 
        User user = jsonSerializer.fromJson(userResponse, UserType.class);
        Account account = accountEntityFacade.find(user.getKey());
        Boolean accountStatus = accountEntityFacade.kredit(account.getId(), value);
        transactionEntityFacade.create(account.getId(), "kredit", value, account.getAccountType());
        return accountStatus;
    }
    
    @Override
    public Boolean debit(String name, long debit) {
        try{
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name); 
            User user = jsonSerializer.fromJson(userResponse, UserType.class);
            Account account = accountEntityFacade.find(user.getKey());
            Boolean status = accountEntityFacade.debit(account.getId(), debit);
            System.out.println("Status: "+status);
            transactionEntityFacade.create(account.getId(), "debit", debit, account.getAccountType());
            return status;
        }catch(Exception e){
            return null;
        }
    }
}

