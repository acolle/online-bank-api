/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anthonycolle
 */
@XmlRootElement
public class Transaction {
    
    // Fields - Member Variables
    private long id;
    private String type;
    private Date dateCreated;
    private String description;
    private double newBalance;
    
    // ADD TRANSACTION AMOUNT

    // Constructors
    public Transaction() {
    }

    public Transaction(long id, String type, String description, double newBalance) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.newBalance = newBalance;
        this.dateCreated = new Date();
    }

    // Setters - Mutators
    public void setId(long id) {
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

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    // Getters - Accessors
    public long getId() {
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

    public double getNewBalance() {
        return newBalance;
    }
    
}
