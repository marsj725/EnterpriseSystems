/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.ArrayList;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.entity.User;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.UserType;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

/**
 *
 * @author marsj725
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();  
    private AccountEntityFacade accountEntityFacade;
    private TransactionEntityFacade transactionEntityFacade;
    
    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade){
        this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public List<Transaction> find(long id) {
        List<Transaction> transaktioner = new ArrayList();
        try{    
            transaktioner = this.transactionEntityFacade.find(id);
            return transaktioner;
        }catch(Exception e){
            return transaktioner;    
        }
    }
}
