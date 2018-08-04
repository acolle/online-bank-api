
package com.mycompany.onlinebankapi.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stephen
 */
@Entity
@XmlRootElement
public class Transfer extends Transaction {

	@OneToOne
	private Account reciever;

	public Account getReciever() {
		return reciever;
	}

	public void setReciever(Account reciever) {
		this.reciever = reciever;
	}
	
	
}
