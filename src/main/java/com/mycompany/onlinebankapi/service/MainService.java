
package com.mycompany.onlinebankapi.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author stephen
 */
public class MainService {
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("BankPersistence");
    private static final EntityManager EM = EMF.createEntityManager();
    private static final EntityTransaction TX = EM.getTransaction();
	
	private MainService(){} //Ensure no instances of this class are created
	
	public static EntityManager getEntityManager() {
		return EM;
	}
	
	public static EntityTransaction getEntityTransaction() {
		return TX;
	}
}
