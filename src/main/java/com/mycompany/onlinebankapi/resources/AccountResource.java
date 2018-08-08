/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Account;
import com.mycompany.onlinebankapi.service.AccountService;
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

@Path("/accounts")
public class AccountResource {
    
    AccountService accountService = new AccountService();

    // Display a list of all accounts for current user
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Account> getAccounts(@CookieParam("mainaccount") Cookie cookie) {
        //TODO : Get only accounts associated with signed in user
		return accountService.retrieveAccounts();
    }
	
	//Gets all accounts from all users (admin only)
	@GET
	@Path("/all")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Account> getAllAccount(@CookieParam("mainaccount") Cookie cookie) {
		return null; //TODO
	}
    
    // Create a new account
    @GET
    @Path("/new")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newAccountForm() {
		//Return form, submit points to POST /accounts with new account
		return null; 
    }
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newAccount(@CookieParam("mainaccount") Cookie cookie, Account account) {
       //Return form, submit points to /accounts POST
		return null; 
    }

    // Display a specific account (get balance)
    @GET
    @Path("/{accountId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account getAccount(@CookieParam("mainaccount") Cookie cookie, @PathParam("accountId") int id) {
        //return accountService.getAccount(id);
		/*
		If account with 'id' does not belong to customer return error message
		with accounts associated with customer
		*/
        return null;
    }
    
}
