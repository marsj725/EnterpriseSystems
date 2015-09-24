/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.List;

public interface Transaction extends Serializable {
    public void setId(int id);
    public int getId();
    public String getType();
    public void setType(String type);
    public int getAmount();
    public void setAmount();
    public String setDate();
    public void getDate();
    public String getStatus();
    public void setStatus();
    public List<Account> getAccount();
    public void setAccount(List<Account> account);
}
