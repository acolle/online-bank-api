/*
 * First Basic Entity of the Online Banking API - User
 * 
 * 
 */
package com.mycompany.onlinebankapi.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anthonycolle
 */

@XmlRootElement
public class User {
    
    // Fields - Member Variables
    private long id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
    private String password;

    // Constructors
    public User() {
    }

    public User(long id, String firstname, String lastname, String address, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    // Setters - Mutators
    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    // Getters - Accessors
    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLasstname() {
        return lastname;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
}
