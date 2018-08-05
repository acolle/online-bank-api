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
	
	//Commonly used error messages
	private final static ErrorMessage EM_NOT_SIGNED_IN = new ErrorMessage("Not signed in, cannot create new transaction.");
	private final static ErrorMessage EM_TRANSACTION_NULL = new ErrorMessage("Must provide transaction details to successfully create transaction through POST.");
	private final static ErrorMessage EM_IMPROPER_TRANSACTION_TYPE = new ErrorMessage("Cannot complete transaction, improper type specified for current transaction.");
	private final static ErrorMessage EM_WRONG_CUSTOMER = new ErrorMessage("Customer profile does not match banking account, transaction failed.");
	
	
	// Display a list of all transactions
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
		if(cookie == null)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(EM_NOT_SIGNED_IN)
					.build();
		if(transaction == null)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(EM_TRANSACTION_NULL)
					.build();
		if(transaction.getAmount() < 0)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new ErrorMessage("Transaction failed: cannot lodge negative sums of money to account."))
					.build();
		String type = transaction.getType();
		if(!type.equals(Transaction.LODGE) || !type.equals(Transaction.DEPOSIT))
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(EM_IMPROPER_TRANSACTION_TYPE)
					.build();
		/*
		Get the current customer
		Check if the account 'to' being accessed is the customers
		Proceed
		*/
		Account to = transaction.getTo();
		if(transaction.getId() != to.getCustomer().getId())
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(EM_WRONG_CUSTOMER)
					.build();
		/*
		If we get to here, everything's good, complete the transaction.
		Add it to the account, update account balance
		*/
		transactionService.lodgeMoney(to.getId(), transaction.getAmount());
		return Response.ok().entity(transactionService.createTransaction(transaction)).build();
	}

    // Withdraw money
    @GET
    @Path("/new/withdraw")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newWithdraw(@Context UriInfo info) {
        
        try {

            // Get the user inputs when lodging money to one of their account
            String account = info.getQueryParameters().getFirst("account");
            String amount = info.getQueryParameters().getFirst("amount");

            // Check if all inputs are correct
            System.out.println("From account: " + account);
            System.out.println("Amount Withdrawn: " + amount);

            if (account.isEmpty() || amount.isEmpty()) {

                // HOW TO RETURN THE SPECIFIC FIELD THAT IS MISSING VALUE?
                String output = "Please fill in all the fields";
                return Response.status(200).entity(output).build();

            } else {
                //transactionService.createTransaction();
                transactionService.withdrawMoney(Integer.parseInt(account), Double.parseDouble(amount));
                String output = "Successful Withdrawal\n" + amount + " have been withdrawn from " + account;
                return Response.status(200).entity(output).build();
            }
        } catch (NullPointerException e) {
            String output = "Error with the parameters passed";
            return Response.status(200).entity(output).build();
        }

    }

    // Transfer money
    @GET
    @Path("/new/transfer")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newTransfer(@Context UriInfo info) {

        try {

            // Get the user inputs when transfering money to another account
            String reciever = info.getQueryParameters().getFirst("recipient");
            String amount = info.getQueryParameters().getFirst("amount");

            // Check if all inputs are correct
            System.out.println("Recipient: " + reciever);
            System.out.println("Amount Transfered: " + amount);

            if (reciever.isEmpty() || amount.isEmpty()) {

                // HOW TO RETURN THE SPECIFIC FIELD THAT IS MISSING VALUE?
                String output = "Please fill in all the fields";
                return Response.status(200).entity(output).build();

            } else {
                //transactionService.createTransaction();
                transactionService.transferMoney(Integer.parseInt(reciever), Double.parseDouble(amount));
                String output = "Successful Transfer\n" + amount + " have been transfered to " + reciever;
                return Response.status(200).entity(output).build();
            }
        } catch (NullPointerException e) {
            String output = "Error with the parameters passed";
            return Response.status(200).entity(output).build();
        }

    }

    // Display a specific transaction
    @GET
    @Path("/{transactionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transaction getTransaction(@PathParam("transactionId") int id) {
        //return transactionService.getTransaction(id);
        return null;
    }

}
