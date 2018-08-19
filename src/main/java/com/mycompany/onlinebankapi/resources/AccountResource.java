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
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 *
 * @author anthonycolle
 */
@Path("/accounts")
public class AccountResource {

    private static final int ADMIN_ACCOUNT = 1;

    AccountService accountService = new AccountService();

	public AccountResource(){}
	
    // Display a list of all accounts for current user
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccounts(@CookieParam("mainaccount") Cookie cookie ) {		
        if (cookie == null)
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Not signed in, cannot get list of accounts."))
                    .build();
		int uid;
		try {
			uid = Hasher.decryptId(cookie.getValue());
		} catch(Exception e) {
			return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Not signed in, cannot get list of accounts."))
                    .build();
		}
		
		//int uid = Hasher.decryptId(cookie.getValue());
        if (uid <= 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Invalid account cookie removed. Log in and try again."))
                    .cookie(new NewCookie(cookie, null, 0, false))
                    .build();
        }
        List<Account> ret = new ArrayList<>();
        for (Account a : accountService.retrieveAccounts()) {
            if (a.getCustomer().getId() == uid) {
                ret.add(a);
            }
        }
        return Response.ok(ret.toArray(new Account[0])).build();
    }

    //Gets all accounts from all users (admin only)
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllAccount(@CookieParam("mainaccount") Cookie cookie) {
        if (cookie == null || Hasher.decryptId(cookie.getValue()) != ADMIN_ACCOUNT) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Cannot view all accounts unless on an admin profile."))
                    .build();
        }
        return Response.ok(accountService.allEntries().toArray(new Account[0])).build();
    }

    // Create a new account object using the parameters passed in the form
    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_PLAIN)
    public Response newAccountForm() {

        // Add some verification if necessary
        return Response.status(200).entity("true").build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newAccount(@CookieParam("mainaccount") Cookie cookie, Account account) {
        
        if (cookie == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Not signed in, cannot create new account."))
                    .build();
        }
        if (account == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new OutputMessage("Must provide account details to create new banking account.")).build();
        }
        int uid = Hasher.decryptId(cookie.getValue());
        if (uid <= 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Invalid account cookie removed. Log in and try again."))
                    .cookie(new NewCookie(cookie, null, 0, false))
                    .build();
        }
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
        if (cookie == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Not signed in, cannot get account."))
                    .build();
        }
        int cid = Hasher.decryptId(cookie.getValue());
        if (cid <= 0) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Invalid account cookie removed. Log in and try again."))
                    .cookie(new NewCookie(cookie, null, 0, false))
                    .build();
        }
        Account a = accountService.retrieveAccount(id);
        if (a.getCustomer().getId() != cid) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new OutputMessage("Cannot access other users accounts."))
                    .build();
        }
        return Response.ok(a).build();
    }

}
