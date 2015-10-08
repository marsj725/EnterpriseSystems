package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    public long getId();
    
    public void setId(long id);
    
    public String getPersonalKey();
    
    public void setPersonalKey(String personalKey);
    
    public String getBankKey();
    
    public void setBankKey(String bankKey);
    
    public int getHoldings();
    
    public void setHoldings(int holdings);
    
    public String getAccountType();

    public void setAccountType(String accountType);
}
