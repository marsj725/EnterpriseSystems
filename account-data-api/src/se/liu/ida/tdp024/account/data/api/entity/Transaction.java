/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Transaction extends Serializable {
    public void setTransactionId(long id);
    public long getTransactionId();
    public String getType();
    public void setType(String type);
    public int getAmount();
    public void setAmount(int amount);
    public String getDate();
    public void setDate(String date);
    public String getStatus();
    public void setStatus(String status);
}
