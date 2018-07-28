/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.service;

import com.mycompany.onlinebankapi.model.Account;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthonycolle
 */
public class AccountService {
    
    // Use ArrayList temporarily to mock the Database layer
    public static List<Account> list = new ArrayList<Account>();

    // ADD ADDITIONAL FUNCTIONS
    // Return a list of all accounts open
    public List<Account> getAllAccounts() {
        Account a1 = new Account(1L, "ABC-123", 2341.50);
        Account a2 = new Account(2L, "DEF-123", 2341.50);
        Account a3 = new Account(3L, "ABC-456", 2341.50);
        Account a4 = new Account(4L, "DEF-456", 2341.50);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        return list;

    }

    // Return a specific account
    public Account getAccount(int id) {

        return list.get(id - 1);

    }
    
}
