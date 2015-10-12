/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

/**
 *
 * @author marsj725
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {
    private AccountEntityFacade accountEntityFacade;
    private TransactionEntityFacade transactionEntityFacade;

    @Override
    public Transaction[] find(String name) {
        Account account = accountEntityFacade.find(name);
        String id = account.getPersonalKey();
        transactionEntityFacade.find(id);
    }
    
}
