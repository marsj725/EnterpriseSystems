package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@Entity
public class TransactionDB implements Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
                 
    private long accountid;
    private long amount;
    private String type;
    private String dateStamp;
    private String status;
    private Account account;
    
    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String getCreated() {
        return dateStamp;
    }

    @Override
    public void setCreated(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Account getAccount() {
        return account;
    }
    
}
