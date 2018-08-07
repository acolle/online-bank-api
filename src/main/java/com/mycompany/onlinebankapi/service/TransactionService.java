/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.service;

import com.mycompany.onlinebankapi.model.Account;
import com.mycompany.onlinebankapi.model.Transaction;
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
public class TransactionService {

	private static EntityManager em = MainService.getEntityManager();
	private static EntityTransaction tx = MainService.getEntityTransaction();
	
    public TransactionService() {}
	
	//Changed to use an account id so a user cannot see other users account transactions
    public List<Transaction> retrieveTransactions(int id) {
        return allEntries(id);
    }

    public List<Transaction> allEntries(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
        Root<Transaction> rootEntity = cq.from(Transaction.class);
        CriteriaQuery<Transaction> all = cq.select(rootEntity).where(cb.equal(rootEntity.get("id"), id));
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
        if (test == null) {
            tx.begin();
            em.persist(b);
            tx.commit();
            em.close();
        }
        return b;
    }

    public void deleteTransaction(int id) {
        Transaction test = em.find(Transaction.class, id);
        if (test != null) {
            tx.begin();
            em.remove(test);
            tx.commit();
            em.close();
        }
    }
    
    public void lodgeMoney(int id, double amount){
        Account recipientAccount = em.find(Account.class, id);
		recipientAccount.addToAccount(amount);
        em.close();
    }
    
    public void withdrawMoney(int id, double amount) {
        Account recipientAccount = em.find(Account.class, id);
        recipientAccount.takeFromAccount(amount);
        em.close();
    }
    
    public void transferMoney(int from, int to, double amount){
        withdrawMoney(from, amount);
		lodgeMoney(to, amount);
        em.close();
    }

}
