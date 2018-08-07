/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Account;
import com.mycompany.onlinebankapi.model.ErrorMessage;
import com.mycompany.onlinebankapi.model.Transaction;
import com.mycompany.onlinebankapi.service.Hasher;
import com.mycompany.onlinebankapi.service.TransactionService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author anthonycolle
 */
@Path("/transactions")
public class TransactionResource {

    TransactionService transactionService = new TransactionService();
	
	//Used for returning error messages
	private static final int I_VALID_TRANSACTION = 0;
	private static final int I_NOT_SIGNED_IN = 1;
	private static final int I_TRANSACTION_NULL = 2;
	private static final int I_TRANSACTION_AMOUNT_EMPTY = 3;
	private static final int I_TYPE_DOES_NOT_MATCH = 4;
	
	//Commonly used error messages
	private final static ErrorMessage EM_NOT_SIGNED_IN = new ErrorMessage("Not signed in, cannot create new transaction.");
	private final static ErrorMessage EM_TRANSACTION_NULL = new ErrorMessage("Must provide transaction details to successfully create transaction through POST.");
	private final static ErrorMessage EM_IMPROPER_TRANSACTION_TYPE = new ErrorMessage("Cannot complete transaction, improper type specified for current transaction.");
	private final static ErrorMessage EM_WRONG_CUSTOMER = new ErrorMessage("Customer profile does not match banking account, transaction failed.");
	private final static ErrorMessage EM_TRANSACTION_EMPTY = new ErrorMessage("Cannot complete transaction with 0 or negative amount.");
	private final static ErrorMessage EM_INTERNAL_ERROR = new ErrorMessage("Internal server error. Please try again.");
	
	// Display a list of all transactions for the customers account currently signed in
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transaction> getTransactions(@CookieParam("mainaccount") Cookie cookie) {
        return transactionService.retrieveTransactions(Hasher.decryptId(cookie.getValue()));
    }

    // Lodge money
    @POST
    @Path("/new/lodge")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newLodge(@CookieParam("mainaccount") Cookie cookie, Transaction transaction) {
		
		switch(validTransaction(cookie, transaction, Transaction.LODGE)) {
			case I_VALID_TRANSACTION:
				//All good, no need to return anything, skip the switch
				break;
			case I_NOT_SIGNED_IN:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_NOT_SIGNED_IN).build();
			case I_TRANSACTION_NULL:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_TRANSACTION_NULL).build();
			case I_TRANSACTION_AMOUNT_EMPTY:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_TRANSACTION_EMPTY).build();
			case I_TYPE_DOES_NOT_MATCH:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_IMPROPER_TRANSACTION_TYPE).build();
			default:
				//Should never be reached, but just in case
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_INTERNAL_ERROR).build();
		}
		
		//If the 'to' account doesn't belong to the current logged in customer, return error
		Account to = transaction.getTo();
		int custId = Hasher.decryptId(cookie.getValue());
		
		//Check if the customer ID in cookie is same as 'to's customer id
		if(custId != to.getCustomer().getId())
			return Response.status(Response.Status.BAD_REQUEST).entity(EM_WRONG_CUSTOMER).build();
		/*
		If we get to here, everything's good, complete the transaction.
		Add it to the account, update account balance
		*/
		transactionService.lodgeMoney(to.getId(), transaction.getAmount());
		return Response.ok().entity(transactionService.createTransaction(transaction)).build();
	}

    // Withdraw money
    @POST
    @Path("/new/withdraw")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newWithdraw(@CookieParam("mainaccount") Cookie cookie, Transaction transaction) {
        
		switch(validTransaction(cookie, transaction, Transaction.WITHDRAW)) {
			case I_VALID_TRANSACTION:
				//All good, no need to return anything, skip the switch
				break;
			case I_NOT_SIGNED_IN:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_NOT_SIGNED_IN).build();
			case I_TRANSACTION_NULL:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_TRANSACTION_NULL).build();
			case I_TRANSACTION_AMOUNT_EMPTY:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_TRANSACTION_EMPTY).build();
			case I_TYPE_DOES_NOT_MATCH:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_IMPROPER_TRANSACTION_TYPE).build();
			default:
				//Should never be reached, but just in case
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_INTERNAL_ERROR).build();
		}
		
		//Check 'from' account if it belongs to current customer
		Account from = transaction.getFrom();
		int custId = Hasher.decryptId(cookie.getValue());
		
		if(custId != from.getCustomer().getId())
			return Response.status(Response.Status.BAD_REQUEST).entity(EM_WRONG_CUSTOMER).build();
		
		transactionService.withdrawMoney(from.getId(), transaction.getAmount());
		return Response.ok().entity(transactionService.createTransaction(transaction)).build();
    }

    // Transfer money
    @POST
    @Path("/new/transfer")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newTransfer(@CookieParam("mainaccount") Cookie cookie, Transaction transaction) {
		
		switch(validTransaction(cookie, transaction, Transaction.TRANSFER)) {
			case I_VALID_TRANSACTION:
				//All good, no need to return anything, skip the switch
				break;
			case I_NOT_SIGNED_IN:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_NOT_SIGNED_IN).build();
			case I_TRANSACTION_NULL:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_TRANSACTION_NULL).build();
			case I_TRANSACTION_AMOUNT_EMPTY:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_TRANSACTION_EMPTY).build();
			case I_TYPE_DOES_NOT_MATCH:
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_IMPROPER_TRANSACTION_TYPE).build();
			default:
				//Should never be reached, but just in case
				return Response.status(Response.Status.BAD_REQUEST).entity(EM_INTERNAL_ERROR).build();
		}
		
		//Check 'from' account if it belongs to current customer
		Account from = transaction.getFrom();
		int custId = Hasher.decryptId(cookie.getValue());
		
		if(custId != from.getCustomer().getId())
			return Response.status(Response.Status.BAD_REQUEST).entity(EM_WRONG_CUSTOMER).build();
        
		//Don't need to check 'to' account
		
		transactionService.transferMoney(from.getId(), transaction.getTo().getId(), transaction.getAmount());
		return Response.ok().entity(transactionService.createTransaction(transaction)).build();

    }

    // Display a specific transaction
    @GET
    @Path("/{transactionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transaction getTransaction(@CookieParam("mainaccount") Cookie cookie, @PathParam("transactionId") int id) {
		//Currently just retrieves the overall transaction with id 'id'. Needs to be account specific
		return transactionService.retrieveTransaction(id);
    }
	
	
	
	private int validTransaction(Cookie c, Transaction t, String type) {
		if(c == null)
			return I_NOT_SIGNED_IN;
		if(t == null)
			return I_TRANSACTION_NULL;
		if(t.getAmount() <= 0)
			return I_TRANSACTION_AMOUNT_EMPTY;
		if(!t.getType().equalsIgnoreCase(type))
			return I_TYPE_DOES_NOT_MATCH;
		
		return I_VALID_TRANSACTION;
	}
	
}
