/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.service;

import com.mycompany.onlinebankapi.model.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthonycolle
 */
public class CustomerService {
    
    // Use ArrayList temporarily to mock the Database layer
    public static List<Customer> list = new ArrayList<Customer>();
    
    
    // ADD ADDITIONAL FUNCTIONS
    
    // Add a new user
    public void addUser(int id, String firstname, String lastname, String address, String email) {

        Customer newUser = new Customer(id, firstname, lastname, address, email);
        System.out.println(firstname + " " + lastname + " " + address);
        list.add(newUser);
        System.out.println("New User Added");
    }

    // Return a list of all users with the service
    public List<Customer> getAllUsers() {
        
//        User u1 = new User(1L, "Bob", "O'Neil", "32, Thomas Street", "boneil@gmail.com", "123456");
//        User u2 = new User(2L, "Michael", "Connor", "23, Jersey Street", "mconnor@gmail.com", "654321");
//        User u3 = new User(3L, "Jane", "Birking", "68, Love Street", "janeb@gmail.com", "456123");
//        User u4 = new User(4L, "Serge", "Ginsburg", "70, Lear Street", "sergeg@gmail.com", "321654");
//        list.add(u1);
//        list.add(u2);
//        list.add(u3);
//        list.add(u4);
        
        return list;
    }

    // Return a specific user of the service
    public Customer getUser(int id) {
        
        return list.get(id - 1);
        
    }
    
}
