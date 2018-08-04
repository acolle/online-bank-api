/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Transaction;
import com.mycompany.onlinebankapi.service.TransactionService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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

    // Display a list of all transactions
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transaction> getTransactions() {
        return transactionService.retrieveTransactions();
    }

    // Lodge money
    @GET
    @Path("/new/lodge")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newLodge(@Context UriInfo info) {
        
        try {

            // Get the user inputs when lodging money to one of their account
            String account = info.getQueryParameters().getFirst("account");
            String amount = info.getQueryParameters().getFirst("amount");

            // Check if all inputs are correct
            System.out.println("To account: " + account);
            System.out.println("Amount Lodged: " + amount);

            if (account.isEmpty() || amount.isEmpty()) {

                // HOW TO RETURN THE SPECIFIC FIELD THAT IS MISSING VALUE?
                String output = "Please fill in all the fields";
                return Response.status(200).entity(output).build();

            } else {
                //transactionService.createTransaction();
                transactionService.lodgeMoney(Integer.parseInt(account), Double.parseDouble(amount));
                String output = "Successful Deposit\n" + amount + " have been lodged to " + account;
                return Response.status(200).entity(output).build();
            }
        } catch (NullPointerException e) {
            String output = "Error with the parameters passed";
            return Response.status(200).entity(output).build();
        }

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
        return transactionService.getTransaction(id);
    }

}
