package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.List;
import javax.persistence.OneToMany;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public class AccountDB implements Account, Transaction {
    private long accountId;
    private long transactionId;
    private String accountType;
    private String personalKey;
    private String bankKey;
    private String transactionType;
    private int holdings;
    private int amount;
    
    @OneToMany(mappedBy = "account", targetEntity = AccountDB.class)
    private List<Transaction> transactions;
    private String date;
    private String status;
    
    @Override
    public long getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(long id) {
        this.accountId = id;
    }

    @Override
    public String getPersonalKey() {
        return personalKey;
    }

    @Override
    public void setPersonalKey(String personalKey) {
        this.personalKey = personalKey;        
    }

    @Override
    public String getBankKey() {
        return bankKey;
    }

    @Override
    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    @Override
    public void setAccountType(String accounttype) {
        this.accountType = accounttype;
    }

    @Override
    public int getHoldings() {
        return holdings;
    }

    @Override
    public void setHoldings(int holdings) {
        this.holdings = holdings;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions; 
    }

    @Override
    public void addTransaction(Transaction transaktion) {
        this.transactions.add(transaktion);
    }
    
    @Override
    public void setTransactionId(long id) {
        this.transactionId = id;
    }

    @Override
    public long getTransactionId() {
        return transactionId;
    }

    @Override
    public String getType() {
        return this.transactionType;
    }

    @Override
    public void setType(String type) {
        this.transactionType = type;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
