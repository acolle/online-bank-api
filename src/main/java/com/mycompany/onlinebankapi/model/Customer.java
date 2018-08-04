/*
 * First Basic Entity of the Online Banking API - User
 * 
 * 
 */
package com.mycompany.onlinebankapi.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anthonycolle
 */
@Entity
@XmlRootElement
public class Customer {
    
    // Fields - Member Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
	@ElementCollection
	private List<Account> accounts = new ArrayList<>();

    // Constructors
    public Customer() {
    }

    public Customer(int id, String firstname, String lastname, String address, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
    }

    // Setters - Mutators
    public void setId(int id) {
        this.id = id;
    }

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

    // Getters - Accessors
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getEmail() {
        return email;
    }
    
	public List<Account> getAccounts() {
		return accounts;
	}
    
}
