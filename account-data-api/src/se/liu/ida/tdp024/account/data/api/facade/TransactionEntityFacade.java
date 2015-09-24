/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.facade;

public interface TransactionEntityFacade {
    
    public String create(String accountType, String name, String bank);
    
    public String find(int id); 
}
