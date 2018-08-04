
package com.mycompany.onlinebankapi.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stephen
 */
@XmlRootElement
public class LoginCredentials {
	private String email;
	private String password;
	
	public LoginCredentials(){}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
