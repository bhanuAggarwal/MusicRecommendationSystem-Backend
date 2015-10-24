/**
 * 
 */
package com.myMusic.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myMusic.domains.Message;
import com.myMusic.domains.User;
import com.myMusic.services.UserService;


/**
 * @author bhanu
 *
 */

@Controller
@RequestMapping("/user")
public class UserController{

	@Autowired
	UserService userService;
	
	Logger LOG = Logger.getLogger("UserController");
	
	/**
	 * This will create the user
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="",method = RequestMethod.POST)
	@ResponseBody
	public Message create(@RequestBody User user) {
		Message result = new Message();
		String message = new String();
		Integer userId = 0;
		if(user != null){
			if(user.validateEmail(user.getEmail())){
				try{
					userId = userService.getUserByEmail(user);
					if(userId == null) {
						userId = userService.create(user);
						if(userId != null)
							message = "New User Created";
						else
							message = "Error While Creating User";
						
					}
					else{
						message = "User Already Exists";
					}
				}catch(Exception e){
					LOG.info("Error While Login : " + e);
				}
			}
			else{
				message = "Invalid Email Id";
			}
		}
		else{
			LOG.info("User is NUll");
		}
		LOG.info(message);
		result.setMessage(message);
		result.setId(userId);
		return result;
	}
}