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
import javax.ws.rs.core.MediaType;

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

    }
    
    // Withdraw money
    @GET
    @Path("/new/withdraw")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newWithdraw(@Context UriInfo info) {

    }
    
    // Transfer money
    @GET
    @Path("/new/transfer")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newTransfer(@Context UriInfo info) {

    }

    // Display a specific transaction
    @GET
    @Path("/{transactionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transaction getTransaction(@PathParam("transactionId") int id) {
        return transactionService.getTransaction(id);
    }
    
}
