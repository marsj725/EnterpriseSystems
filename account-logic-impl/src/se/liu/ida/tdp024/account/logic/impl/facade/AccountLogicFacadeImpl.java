package se.liu.ida.tdp024.account.logic.impl.facade;

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
    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade){
        this.accountEntityFacade = accountEntityFacade;
    }
       
    @Override
    public List<Account> find(String name) {
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
        try{
            String bankResponse = httpHelper.get("http://enterprise-systems.appspot.com/bank" + "/find.name/", "name", bank);
            String userResponse = httpHelper.get("http://enterprise-systems.appspot.com/person" + "/find.name/", "name", name); 
            User user = jsonSerializer.fromJson(userResponse, UserType.class);
            User banks = jsonSerializer.fromJson(bankResponse, UserType.class);
            System.out.println("BXK:" + bankResponse + " UXR: "+userResponse);

            if(bankResponse == null || userResponse == null){return "FAILED";}
            if(accountType == "SAVINGS" || accountType == "CREDIT"){
                
                System.out.println("they are not empty! BNK: "+ bankResponse + " USR: "+ userResponse +" AccountT: "+accountType);
                Account konto = accountEntityFacade.create(accountType, user.getKey(), banks.getKey());
                if(!konto.getPersonalKey().isEmpty()){
                    return "OK";
                }else{
                    System.out.println("FAIL @ 1");
                    return "FAILED";
                }
            }else{
                System.out.println("FAIL @ 2");
                return "FAILED";
            }
        }catch(Exception e){
            System.out.println("FAIL @ E");
            return "FAILED";
        }
    }

    @Override
    public Boolean credit(long id,long credit) {
        String status = null;
        Boolean accountStatus = accountEntityFacade.kredit(id, credit);
        if(accountStatus){status = "OK";}
        else{status = "FAILED";}
        transactionEntityFacade.create(id, "credit", credit, status);
        return accountStatus;
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
            return null;
        }
    }
}

