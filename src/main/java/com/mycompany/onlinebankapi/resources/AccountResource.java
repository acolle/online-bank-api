/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Account;
import com.mycompany.onlinebankapi.service.AccountService;
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

@Path("/accounts")
public class AccountResource {
    
    AccountService accountService = new AccountService();

    // Display a list of all accounts for current user
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> getAccounts() {
        return accountService.retrieveAccounts();
    }
    
    // Create a new account
    @GET
    @Path("/new")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newAccount(@Context UriInfo info) {
       return null; 
    }

    // Display a specific account (get balance)
    @GET
    @Path("/accounts/{accountId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account getAccount(@PathParam("accountId") int id) {
        //return accountService.getAccount(id);
        return null;
    }
    
}
