/**
 * 
 */
package com.myMusic.domains;

/**
 * @author bhanu
 *
 */
public class Session {
	Integer id;
	Integer userId;
	String session;
	Double rating;
	
	public Session() {
		super();
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", userId=" + userId + ", session="
				+ session + ", rating=" + rating + "]";
	}

	public Session(Integer id, Integer userId, String session, Double rating) {
		super();
		this.id = id;
		this.userId = userId;
		this.session = session;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
}