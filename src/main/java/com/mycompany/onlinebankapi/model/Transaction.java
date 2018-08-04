/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 *
 * @author anthonycolle
 */
@MappedSuperclass
public class Transaction {
	
	public static final String
			DEPOSIT = "DEPOSIT",
			LODGE = "LODGE",
			WITHDRAW = "WITHDRAW",
			TRANSFER = "TRANSFER";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String type;
    private Date dateCreated;
    private String description;
    private double amount;
	@OneToOne
	private Account account;
	
    
    // ADD TRANSACTION AMOUNT

    // Constructors
    public Transaction() {
    }

    public Transaction(int id, String type, String description, double amount) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.dateCreated = new Date();
    }

    // Setters - Mutators
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getters - Accessors
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
    
}
