package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.ArrayList;
import java.util.List;
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
    private TransactionEntityFacade transactionEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade,TransactionEntityFacade transactionEntityFacade){
        this.accountEntityFacade = accountEntityFacade;
        this.transactionEntityFacade = transactionEntityFacade;
    }
       
    @Override
    public List<Account> find(String name) {
        List<Account> accounts = new ArrayList();
        try{
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name);     
            User user = jsonSerializer.fromJson(userResponse, UserType.class);
            accounts = accountEntityFacade.find(user.getKey());
            return accounts;
        }catch(Exception e){
            return accounts;
        }
    }

    @Override
    public String create(String accountType, String name, String bank) {
        String returnStatus = "FAILED";
        try{
            String bankResponse = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name); 
            User user = jsonSerializer.fromJson(userResponse, UserType.class);
            User banks = jsonSerializer.fromJson(bankResponse, UserType.class);
            System.out.println("BXK:" + bankResponse + " UXR: "+userResponse);

            if(bankResponse == null || userResponse == null){return "FAILED";}
            else{
                System.out.println("AccountType:" + accountType);
                if(accountType.contains("SAVINGS") || accountType.contains("CHECK")){
                    Account konto = accountEntityFacade.create(accountType, user.getKey(), banks.getKey());
                    if(!konto.getPersonalKey().isEmpty()){
                        System.out.println("--------------------------------------------OK---------------------------------------------------");
                        return "OK";
                    }
                }
                return "FAILED";
            }
        }catch(Exception e){
            System.out.println("FAIL @ E");
            return "FAILED";
        }
    }

    @Override
    public Boolean credit(long id,long credit) {
        try{
            String status = null;
            Boolean accountStatus = accountEntityFacade.kredit(id, credit);
            if(accountStatus){status = "OK";}
            else{status = "FAILED";}
            transactionEntityFacade.create(id, "credit", credit, status);
            return accountStatus;
        }catch(Exception e){
            return false;
        }
    }
    
    @Override
    public Boolean debit(long id, long debit) {
        try{
            String status = null;
            Boolean accountStatus = accountEntityFacade.debit(id, debit);
            if(accountStatus){status = "OK";}
            else{status = "FAILED";}
            transactionEntityFacade.create(id, "debit", debit, status);
            return accountStatus;
        }catch(Exception e){
            return false;
        }
    }
}

