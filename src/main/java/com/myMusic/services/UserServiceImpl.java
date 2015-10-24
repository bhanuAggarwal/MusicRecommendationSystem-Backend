/**
 * 
 */
package com.myMusic.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMusic.domains.User;
import com.myMusic.mappers.UserMapper;


/**
 * @author bhanu
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	
	Logger LOG = Logger.getLogger("UserService");
	
	@Override
	public Integer create(User user) {
		Integer userId = 0;
		int i = 0;
		if(user != null){
			try{
				i = userMapper.createUser(user);
				if(i == 1){
					userId = userMapper.getUserByEmail(user);
				}
				
			} catch(Exception e){
			    LOG.info("Fault in UserMapper" + e);
			}
	    } else {
			LOG.info("User data is null");
		}
		return userId;
	}

	@Override
	public Integer getUserByEmail(User user) {
		Integer userId = 0;
		if(user != null){
			try{
				userId = userMapper.getUserByEmail(user);
			}catch(Exception e){
				LOG.info("Error While Getting User : " + e);
			}
		}
		else{
			LOG.info("User is NULL");
		}
		return userId;
	}
	

}
