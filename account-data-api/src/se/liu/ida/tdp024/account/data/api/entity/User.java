/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

/**
 *
 * @author masj
 */
public interface User {
    
    public void setKey(String key);
    
    public String getKey();
    
    public void setName(String name);
    
    public String getName();
}
