/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.service;

import com.mycompany.onlinebankapi.model.Account;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author anthonycolle
 */
public class AccountService {
	
	private static EntityManager em;
	private static EntityTransaction tx;
	
    public AccountService() {
		em = MainService.getEntityManager();
		tx = MainService.getEntityTransaction();
	}
	
    public List<Account> retrieveAccounts() {
		return allEntries();
	}
	
	public List<Account> allEntries() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> cq = cb.createQuery(Account.class);
		Root<Account> rootEntity = cq.from(Account.class);
		CriteriaQuery<Account> all = cq.select(rootEntity);
		TypedQuery<Account> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}
	
	public Account retrieveAccount(int id) {
		Account test = em.find(Account.class, id);
		em.close();
		return test;
	}
	
	public Account createAccount(Account b) {
		Account test = em.find(Account.class, b.getId());
		if(test==null) {
			tx.begin();
			em.persist(b);
			tx.commit();
			em.close();
		}
		return b;
	}
	
	public void deleteAccount(int id) {
		Account test = em.find(Account.class, id);
		if(test!=null) {
			tx.begin();
			em.remove(test);
			tx.commit();
			em.close();
		}
	}
    
}
