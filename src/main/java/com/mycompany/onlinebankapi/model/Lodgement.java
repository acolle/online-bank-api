
package com.mycompany.onlinebankapi.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stephen
 */
@Entity
@XmlRootElement
public class Lodgement extends Transaction {
	
	private String creditCard;
	
	public Lodgement(){}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	
	
}
