/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.model;

/**
 *
 * @author anthonycolle
 */
public class Account {
    
    // Fields - Member Variables
    private long id;
    private String accountNo;
    private double balance;
    private long[] transactionHistory; // how to handle that?
    // Add type? Current and Savings?

    
    // Constructors
    public Account() {
    }

    public Account(long id, String accountNo, double balance) {
        this.id = id;
        this.accountNo = accountNo;
        this.balance = balance;
        //this.transactionHistory = transactionHistory;
    }

    // Setters - Mutators
    public void setId(long id) {
        this.id = id;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTransactionHistory(long[] transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    // Getters - Accessors
    public long getId() {
        return id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public long[] getTransactionHistory() {
        return transactionHistory;
    }
    
}
