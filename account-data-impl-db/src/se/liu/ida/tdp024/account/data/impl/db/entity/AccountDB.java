package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.List;
import javax.persistence.OneToMany;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public class AccountDB implements Account {
    private long id;
    private String accountType;
    private String personalKey;
    private String bankKey;
    private int holdings;
    
    @OneToMany(mappedBy = "account", targetEntity = AccountDB.class)
    private List<transactions> transactions;
    
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
       this.id = id;
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
    
}
