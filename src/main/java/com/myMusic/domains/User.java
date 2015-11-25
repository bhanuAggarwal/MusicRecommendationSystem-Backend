/**
 * 
 */
package com.myMusic.domains;

import java.sql.Timestamp;


/**
 * @author bhanu
 *
 */
public class User {
	
	Integer id;
	String name;
	String email;
	String password;
	Timestamp creation_time;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email
				+ ", password=" + password + ", creation_time=" + creation_time
				+ "]";
	}

	public User() {
		super();
	}

	public User(Integer id, String name, String email, String password,
			Timestamp creation_time) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.creation_time = creation_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
	
	
	public Timestamp getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(Timestamp creation_time) {
		this.creation_time = creation_time;
	}

	/**
	 * This function will validate the email if of user
	 * 
	 * @return
	 */
	public boolean validateEmail(String email_id){
		 String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		 if(email_id.matches(EMAIL_REGEX))
			 return true;
		 else
			 return false;
	}
}