/**
 * 
 */
package com.myMusic.services;

import com.myMusic.domains.User;

/**
 * @author bhanu
 *
 */
public interface UserService {
	
	public Integer create(User user);

	public Integer getUserByEmail(User user);

	

}
	
