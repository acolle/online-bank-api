/*
 * First Basic Entity of the Online Banking API - User
 * 
 * 
 */
package com.mycompany.onlinebankapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author anthonycolle
 */
@Entity
@Table
@XmlRootElement
public class Customer implements Serializable {

    // Fields - Member Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
	
//	Tried using each of these annotations to prevent password and accounts being
//	sent back to user, neither working as of now.
//	@XmlTransient
//	@JsonIgnore
	
    private String password;
	@OneToMany
    private List<Account> accounts = new ArrayList<>();
	
	public boolean hasAccount(int accId) {
		for(Account a : accounts)
			if(a.getId() == accId)
				return true;
		return false;
	}
	
    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

}
