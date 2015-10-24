/**
 * 
 */
package com.myMusic.domains;

/**
 * @author bhanu
 *
 */
public class Message {
	
	String message;
	Integer id;
    
	/**
	 * 
	 */
	public Message() {
		super();
	}
	
	public Message(String message, Integer id) {
		super();
		this.message = message;
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
}
