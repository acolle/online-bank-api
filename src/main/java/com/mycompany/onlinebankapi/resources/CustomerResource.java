/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Customer;
import com.mycompany.onlinebankapi.model.LoginCredentials;
import com.mycompany.onlinebankapi.model.OutputMessage;
import com.mycompany.onlinebankapi.service.CustomerService;
import com.mycompany.onlinebankapi.service.Hasher;
import java.util.List;
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
					.entity(new OutputMessage("Not signed in, cannot retrieve profile."))
					.build();
		try {
			return Response
					.ok(CustomerService.retrieveCustomer(Hasher.decryptId(cookie.getValue())))
					.build();
		} catch(Exception e) {
			//Error with cookie, remove it
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new OutputMessage("Invalid log in details, cleared invalid cookie. Please log in again."))
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
					.entity(new OutputMessage("Cannot view all accounts unless on an admin profile."))
					.build();
		
		//Errors were being formed when trying to convert ArrayList to json so convert to array and dump that.
		List<Customer> all = userService.allEntries();
		Customer[] allA = all.toArray(new Customer[0]);
		
		return Response.ok(allA).build();
    }
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response newCustomer(Customer newCust) {
		if(newCust == null)
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new OutputMessage("Must provide details to create new Customer account.")).build();
		for(Customer c : userService.allEntries())
			if(c.getEmail().equals(newCust.getEmail()))
				return Response.status(Response.Status.FORBIDDEN).entity(new OutputMessage("Customer with that email already exists.")).build();
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
		return Response.serverError().entity(new OutputMessage("Problem saving new account. Please try again.")).build();
	}
	

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response login(@CookieParam("mainaccount") Cookie cookie, LoginCredentials loginCredentials) throws Hasher.CannotPerformOperationException {
        if (cookie != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new OutputMessage("Already logged in.")).build();
        }
		if(loginCredentials == null)
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new OutputMessage("Must provide log in details.")).build();
		
        for (Customer c : userService.allEntries()) {
            if (c.getEmail().equals(loginCredentials.getEmail())) {
				try {
					if(Hasher.verifyPassword(loginCredentials.getPassword(), c.getPassword())) {
						NewCookie nc = new NewCookie(
								"mainaccount",				//Name of cookie
								Hasher.encryptId(c.getId()),//Value, the encrypted id
								"/",						//Path, set to site-wide
								null,						//Domain, not needed
								null,						//Comment, not needed
								3600,						//Time in seconds until cookie expires - 1hr
								false);						//Can be sent over any connection
						return Response.ok(new OutputMessage("Successfully logged in.")).cookie(nc).build();
					} else break;
				} catch (Hasher.InvalidHashException ex) {
					return Response.serverError().entity(new OutputMessage("Exception has occurred. Please try again.")).build();
				}
			}
		}
        return Response.status(Response.Status.BAD_REQUEST).entity(new OutputMessage("Incorrect email or password.")).build();
    }

    @POST
    @Path("/logout")
    public Response logout(@CookieParam("mainaccount") Cookie cookie) {
        if (cookie != null) {
            NewCookie nc = new NewCookie(cookie, null, 0, false);
            return Response.ok(new OutputMessage("Sucessfully logged out.")).cookie(nc).build();
        }
        return Response.ok(new OutputMessage("Already logged out.")).build();
    }

//    // PREVIOUS - EXISTING CUSTOMER CANNOT CREATE NEW PROFILE
//    @GET
//    @Path("/new")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response newUser(@CookieParam("mainaccount") Cookie cookie) {
//		if(cookie != null) {
//			//User is already signed in. React accordingly
//			//Tell user to log out first if they want to create a new customer account
//		}
//		//User not signed in, show html form for creating a new customer
//		//and on submit point to POST
//		
//		//for now (placeholder)
//		return Response.serverError().entity("Currently not implemented.").build();
//    }
    
    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_PLAIN)
    public Response newCustomerForm() {
        
        // Add some verification if necessary
        return Response.status(200).entity("true").build();
        
    }

    // Display certain details about a specific user (admin)
    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getUser(@CookieParam("mainaccount") Cookie cookie, @PathParam("userId") int id) {
		if(cookie == null)
			return Response.status(Response.Status.BAD_REQUEST).entity(new OutputMessage("Cannot view profile unless signed in.")).build();
		int uid = Hasher.decryptId(cookie.getValue());
		if(uid == ADMIN_ACCOUNT)
			return Response.ok(CustomerService.retrieveCustomer(id)).build();
		if(uid != id)
			return Response.status(Response.Status.BAD_REQUEST).entity(new OutputMessage("Cannot view other peoples profiles.")).build();
		return Response.ok(CustomerService.retrieveCustomer(id)).build();
	}
	
}
