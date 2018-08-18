/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Customer;
import com.mycompany.onlinebankapi.model.ErrorMessage;
import com.mycompany.onlinebankapi.model.LoginCredentials;
import com.mycompany.onlinebankapi.service.CustomerService;
import com.mycompany.onlinebankapi.service.Hasher;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author anthonycolle
 */
@Path("/user")
public class CustomerResource {

    CustomerService userService = new CustomerService();
	
	private static final int ADMIN_ACCOUNT = 1;
	
    public CustomerResource() {}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getCurrentCustomer(@CookieParam("mainaccount") Cookie cookie) {
		if(cookie == null)
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new ErrorMessage("Not signed in, cannot retrieve profile."))
					.build();
		try {
			return Response
					.ok(CustomerService.retrieveCustomer(Hasher.decryptId(cookie.getValue())))
					.build();
		} catch(Exception e) {
			//Error with cookie, remove it
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new ErrorMessage("Invalid log in details, cleared invalid cookie. Please log in again."))
					.cookie(new NewCookie(cookie, null, 0, false))
					.build();
		}
	}
	
	// Display a list of all users (Admin)    
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getUsers(@CookieParam("mainaccount") Cookie cookie) {
		if(cookie == null || Hasher.decryptId(cookie.getValue()) != ADMIN_ACCOUNT)
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(new ErrorMessage("Cannot view all accounts unless on an admin profile."))
					.build();
		return Response.ok(userService.allEntries()).build();
    }
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response newCustomer(Customer newCust) {
		try {
			//Update the password
			newCust.setPassword(Hasher.createHash(newCust.getPassword()));
			Customer ret = userService.createCustomer(newCust);
			return Response
					.ok(ret)
					.cookie(new NewCookie("mainaccount", Hasher.encryptId(ret.getId())))
					.build();
		} catch (Hasher.CannotPerformOperationException ex) {
			Logger.getLogger(CustomerResource.class.getName()).log(Level.SEVERE, null, ex);
		}
		return Response.serverError().entity(new ErrorMessage("Problem saving new account. Please try again.")).build();
	}
	

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response login(@CookieParam("mainaccount") Cookie cookie, LoginCredentials loginCredentials) throws Hasher.CannotPerformOperationException {
        if (cookie != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Already logged in.").build();
        }
        for (Customer c : userService.allEntries()) {
            if (c.getEmail().equals(loginCredentials.getEmail())) {
                if (c.getPassword().equals(Hasher.createHash(c.getPassword()))) {
                    //User successfully logged in
                    //Set a cookie in their browser and response accordingly
                    NewCookie nc = new NewCookie("mainaccount", Hasher.encryptId(c.getId()));
                    return Response.ok("Successfully logged in.").cookie(nc).build();
                } else //Incorrect password
                //Break and return the default message
                {
                    break;
                }
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Incorrect email or password.").build();
    }

    @POST
    @Path("/logout")
    public Response logout(@CookieParam("account") Cookie cookie) {
        if (cookie != null) {
            NewCookie nc = new NewCookie(cookie, null, 0, false);
            return Response.ok("Sucessfully logged out.").cookie(nc).build();
        }
        return Response.ok("Already logged out.").build();
    }

    // Create a new user via html form
    @GET
    @Path("/new")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newUser(@CookieParam("mainaccount") Cookie cookie) {
		if(cookie != null) {
			//User is already signed in. React accordingly
			//Tell user to log out first if they want to create a new customer account
		}
		//User not signed in, show html form for creating a new customer
		//and on submit point to POST
		
		//for now (placeholder)
		return Response.serverError().entity("Currently not implemented.").build();
    }

    // Display certain details about a specific user (admin)
    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getUser(@CookieParam("mainaccount") Cookie cookie, @PathParam("userId") int id) {
		if(cookie == null)
			return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("Cannot view profile unless signed in.")).build();
		if(Hasher.decryptId(cookie.getValue()) != id)
			return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("Cannot view other peoples profiles.")).build();
		return Response.ok(CustomerService.retrieveCustomer(id)).build();
	}

}
