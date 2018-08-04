/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.service;

import com.mycompany.onlinebankapi.model.Transaction;
import com.mycompany.onlinebankapi.model.Transaction;
import java.util.ArrayList;
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
public class TransactionService {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Transaction");
	private EntityManager em = emf.createEntityManager();
	private EntityTransaction tx = em.getTransaction();
    
    
    public List<Transaction> retrieveTransactions() {
		return allEntries();
	}
	
	public List<Transaction> allEntries() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
		Root<Transaction> rootEntity = cq.from(Transaction.class);
		CriteriaQuery<Transaction> all = cq.select(rootEntity);
		TypedQuery<Transaction> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}
	
	public Transaction retrieveTransaction(int id) {
		Transaction test = em.find(Transaction.class, id);
		em.close();
		return test;
	}
	
	public Transaction createTransaction(Transaction b) {
		Transaction test = em.find(Transaction.class, b.getId());
		if(test==null) {
			tx.begin();
			em.persist(b);
			tx.commit();
			em.close();
		}
		return b;
	}
	
	public void deleteTransaction(int id) {
		Transaction test = em.find(Transaction.class, id);
		if(test!=null) {
			tx.begin();
			em.remove(test);
			tx.commit();
			em.close();
		}
	}
    
}
