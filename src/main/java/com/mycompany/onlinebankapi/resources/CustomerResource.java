/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.onlinebankapi.resources;

import com.mycompany.onlinebankapi.model.Customer;
import com.mycompany.onlinebankapi.model.LoginCredentials;
import com.mycompany.onlinebankapi.service.CustomerService;
import com.mycompany.onlinebankapi.service.Hasher;
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

@Path("/user")
public class CustomerResource {
    
    CustomerService userService = new CustomerService();
	
	public CustomerResource(){}
	
	@POST
	@Path("/login")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response login(@CookieParam("account") Cookie cookie, LoginCredentials loginCredentials) throws Hasher.CannotPerformOperationException {
		if(cookie != null)
			return Response.serverError().entity("Already logged in.").build();
		for(Customer c : userService.allEntries())
			if(c.getEmail().equals(loginCredentials.getEmail()))
				if(c.getPassword().equals(Hasher.createHash(c.getPassword()))) {
					//User successfully logged in
					//Set a cookie in their browser and response accordingly
					NewCookie nc = new NewCookie("account", Hasher.createHash(Integer.toString(c.getId())));
					return Response.ok("Successfully logged in.").cookie(nc).build();
				} else
					//Incorrect password
					//Break and return the default message
					break;
		
		return Response.serverError().entity("Incorrect email or password.").build();
	}
	
	@POST
	@Path("/logout")
	public Response logout(@CookieParam("account") Cookie cookie) {
		if(cookie != null) {
			NewCookie nc = new NewCookie(cookie, null, 0, false);
			return Response.ok("Sucessfully logged out.").cookie(nc).build();
		}
		return Response.ok("Already logged out.").build();
	}
	
    
    // Display a list of all users (Admin)    
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> getUsers() {
        return userService.retrieveCustomers();
    }
    
    // Create a new user
    @GET
    @Path("/new")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newUser(@Context UriInfo info) {
                
        try {
            
//            // Check values from UriInfo
//            System.out.println("absolutePath: " + info.getAbsolutePath());
//            System.out.println("baseUri: " + info.getBaseUri());
//            System.out.println("matchedResource: " + info.getMatchedResources());
//            System.out.println("matchedUri: " + info.getMatchedURIs());
//            System.out.println("path: " + info.getPath());
//            System.out.println("pathParameters: " + info.getPathParameters());
//            System.out.println("pathSegments: " + info.getPathSegments());
//            System.out.println("queryParameters: " + info.getQueryParameters());
//            System.out.println("requestUri: " + info.getRequestUri());

            // Get the user inputs when creating a new profile
            String firstname = info.getQueryParameters().getFirst("firstname");
            String lastname = info.getQueryParameters().getFirst("lastname");
            String address = info.getQueryParameters().getFirst("address");
            String email = info.getQueryParameters().getFirst("email");
            String password = info.getQueryParameters().getFirst("password");
            String passwordConfirm = info.getQueryParameters().getFirst("password2");

        // Check if all inputs are correct
        System.out.println("Firstname: " + firstname);
        System.out.println("Lastname: " + lastname);
        System.out.println("Address: " + address);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Password Confirmation: " + passwordConfirm);

            if (firstname.isEmpty() || lastname.isEmpty()
                    || address.isEmpty() || email.isEmpty()
                    || password.isEmpty() || passwordConfirm.isEmpty()) {

                // HOW TO RETURN FIELDS THAT HAVE MISSING VALUES?
                String output = "Please fill in all the fields";
                return Response.status(200).entity(output).build();
                
            } else if (!password.equals(passwordConfirm)) {
                String output = "Sorry the passwords do no match";
                return Response.status(200).entity(output).build();
                
            } else {
                userService.createCustomer(1, firstname, lastname, address, email);
                String output = "Welcome " + firstname + "!\nGet started by opening a new account";
                return Response.status(200).entity(output).build();
            }
        } catch (NullPointerException e){
            String output = "Error with the parameters passed";
            return Response.status(200).entity(output).build();
        }       
    }

    // Display certain details about a specific user
    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer getUser(@PathParam("userId") int id) {
        return userService.getUser(id);
    }
    
	
}
