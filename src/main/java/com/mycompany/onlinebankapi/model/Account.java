/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anthonycolle
 */
@Entity
@XmlRootElement
public class Account implements Serializable {

    // Fields - Member Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String sortCode;
    private String accountNo;
    private double balance;
    private boolean savingsAccount;
    @ManyToOne
    private Customer customer;
    @ElementCollection
    private List<Transaction> transactionHistory = new ArrayList<>();

    public void addToAccount(double amount) {
        this.balance += amount;
    }

    public void takeFromAccount(double amount) {
        this.balance -= amount;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(boolean savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

}
