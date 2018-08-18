/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Account;
import com.mycompany.onlinebankapi.model.OutputMessage;
import com.mycompany.onlinebankapi.service.AccountService;
import com.mycompany.onlinebankapi.service.CustomerService;
import com.mycompany.onlinebankapi.service.Hasher;
import java.util.ArrayList;
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
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author anthonycolle
 */

@Path("/accounts")
public class AccountResource {
    
	private static final int ADMIN_ACCOUNT = 1;
	
    AccountService accountService = new AccountService();

    // Display a list of all accounts for current user
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccounts(@CookieParam("mainaccount") Cookie cookie) {
        //TODO : Get only accounts associated with signed in user
		if(cookie == null)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Not signed in, cannot get list of accounts."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		if(Hasher.decryptId(cookie.getValue()) <= 0)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Invalid account cookie removed. Log in and try again."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		int uid = Hasher.decryptId(cookie.getValue());
		List<Account> ret = new ArrayList<>();
		for(Account a : accountService.retrieveAccounts())
			if(a.getCustomer().getId() == uid)
				ret.add(a);
		return Response.ok(ret).build();
    }
	
	//Gets all accounts from all users (admin only)
	@GET
	@Path("/all")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAllAccount(@CookieParam("mainaccount") Cookie cookie) {
		if(cookie == null || Hasher.decryptId(cookie.getValue()) != ADMIN_ACCOUNT)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Cannot view all accounts unless on an admin profile."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		return Response.ok(accountService.allEntries()).build();
	}
    
    // Create a new account object using the parameters passed in the form
    @GET
    @Path("/new")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response newAccountForm() {
        System.out.println("****\n\n");
        System.out.println("Reach here");
        System.out.println("\n\n****");
                
        String output = "true";
	return Response.status(200).entity(output).build();        
    }
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newAccount(@CookieParam("mainaccount") Cookie cookie, Account account) {
		if(cookie == null)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Not signed in, cannot create new account."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		if(account == null)
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new OutputMessage("Must provide account details to create new banking account.")).build();
		int uid = Hasher.decryptId(cookie.getValue());
		if(uid <= 0)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Invalid account cookie removed. Log in and try again."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		//Make sure the customer association is set properly for the signed in user
		account.setCustomer(CustomerService.retrieveCustomer(uid));
		return Response.ok(accountService.createAccount(account)).build();
    }

    // Display a specific account (get balance)
    @GET
    @Path("/{accountId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccount(@CookieParam("mainaccount") Cookie cookie, @PathParam("accountId") int id) {
        //return accountService.getAccount(id);
		/*
		If account with 'id' does not belong to customer return error message
		with accounts associated with customer
		*/
		if(cookie == null)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Not signed in, cannot get account."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		int cid = Hasher.decryptId(cookie.getValue());
		if(cid <= 0)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Invalid account cookie removed. Log in and try again."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		Account a = accountService.retrieveAccount(id);
		if(a.getCustomer().getId()!=cid)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new OutputMessage("Cannot access other users accounts."))
					.build();
		return Response.ok(a).build();
	}
    
}
