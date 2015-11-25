/**
 * 
 */
package com.myMusic.services;

import java.util.List;

import com.myMusic.domains.Song;
import com.myMusic.domains.User;
import com.myMusic.domains.UserTaste;

/**
 * @author bhanu
 *
 */
public interface UserService {
	
	public Integer create(User user);
	public Integer validateLogin(User user);
	public Integer addUserTaste(Integer userId, List<Song> songsList);
	public Integer updateUserTaste(Integer integer, List<Song> list);
	public UserTaste getUserTaste(Integer userId);
}
	
