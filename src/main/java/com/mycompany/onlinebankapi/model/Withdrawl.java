
package com.mycompany.onlinebankapi.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stephen
 */

@Entity
@XmlRootElement
public class Withdrawl extends Transaction {
	
	private String card;

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	
	
}
