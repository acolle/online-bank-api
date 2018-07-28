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

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Transaction> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @GET
    @Path("/{transactionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Transaction getUser(@PathParam("transactionId") int id) {
        return transactionService.getTransaction(id);
    }
    
}
