/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.service;

import com.mycompany.onlinebankapi.model.Transaction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthonycolle
 */
public class TransactionService {
    
    // Use ArrayList temporarily to mock the Database layer
    public static List<Transaction> list = new ArrayList<Transaction>();

    // ADD ADDITIONAL FUNCTIONS
    
    // Return a list of all transaction to date
    public List<Transaction> getAllTransactions() {
        Transaction t1 = new Transaction(1L, "debit", "description1", 2341.50);
        Transaction t2 = new Transaction(1L, "debit", "description2", 534.20);
        Transaction t3 = new Transaction(1L, "credit", "description3", 11234.00);
        Transaction t4 = new Transaction(1L, "debit", "description4", 5432.65);

        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        return list;

    }

    // Return a specific transaction
    public Transaction getTransaction(int id) {

        return list.get(id - 1);

    }
    
}
