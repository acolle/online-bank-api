/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.service;

import com.mycompany.onlinebankapi.model.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author anthonycolle
 */
public class CustomerService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Customer");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    public List<Customer> retrieveCustomers() {
        return allEntries();
    }

    public List<Customer> allEntries() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> rootEntity = cq.from(Customer.class);
        CriteriaQuery<Customer> all = cq.select(rootEntity);
        TypedQuery<Customer> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public Customer retrieveCustomer(int id) {
        Customer test = em.find(Customer.class, id);
        em.close();
        return test;
    }

    public Customer createCustomer(Customer b) {
        Customer test = em.find(Customer.class, b.getId());
        if (test == null) {
            tx.begin();
            em.persist(b);
            tx.commit();
            em.close();
        }
        return b;
    }

    public void deleteCustomer(int id) {
        Customer test = em.find(Customer.class, id);
        if (test != null) {
            tx.begin();
            em.remove(test);
            tx.commit();
            em.close();
        }
    }

	public static String hashPass(String password) {
		//TODO
		//Will one-way hash the already half-way hashed password user sends
	}
	
	public static String hashId(int id) {
		
	}
	
	public static int unhashId(String hashedId) {
		
	}
}
