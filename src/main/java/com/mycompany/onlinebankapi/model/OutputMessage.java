
package com.mycompany.onlinebankapi.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author stephen
 */
@XmlRootElement
public class OutputMessage {
	private String message;

	public OutputMessage() {
	}

	public OutputMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
