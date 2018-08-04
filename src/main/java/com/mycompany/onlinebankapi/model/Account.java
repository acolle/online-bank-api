/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anthonycolle
 */
@Entity
@XmlRootElement
public class Account {
    
    // Fields - Member Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String sortCode;
    private String accountNo;
    private double balance;
	@OneToOne
	private Customer customer;
	@ElementCollection
	private List<Transaction> transactionHistory = new ArrayList<>();

    
    // Constructors
    public Account() {
    }

    public Account(int id, String accountNo, double balance, List<Transaction> transactionHistory) {
        this.id = id;
        this.accountNo = accountNo;
        this.balance = balance;
		this.transactionHistory = transactionHistory;
    }

    // Setters - Mutators
    public void setId(int id) {
        this.id = id;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Getters - Accessors
    public int getId() {
        return id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

	public List<Transaction> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(List<Transaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}
	
	
    
}
