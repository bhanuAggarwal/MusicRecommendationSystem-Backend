/**
 * 
 */
package com.myMusic.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myMusic.domains.Message;
import com.myMusic.domains.Session;
import com.myMusic.domains.Song;
import com.myMusic.domains.SongListen;
import com.myMusic.domains.User;
import com.myMusic.services.SessionService;
import com.myMusic.services.SongService;
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
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	SongService songServices;
	
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
		Integer userId = 0, sessionFlag = 0;
		if(user != null){
			if(user.validateEmail(user.getEmail())){
				try{
					userId = userService.validateLogin(user);
					if(userId.equals(0)) {
						userId = userService.create(user);
						if(userId > 0){
							message = "New User Created";
							sessionFlag = sessionService.startSession(userId);
							if(sessionFlag > 0){
								LOG.info("Session Started Successfully");
							}
							else{
								LOG.info("Session Can't be Started");
							}
						}
						else
							message = "Error While Creating User";
						
					}
					else if(userId.equals(-1)){
						message = "Incorrect Password";
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
	/**
	 * This will update Session
	 * @param userId
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/session",method = RequestMethod.POST)
	@ResponseBody
	public Message updateSession(@RequestBody Session session){
		Message result = new Message();
		String message = new String();
		Integer flag = 0;
		SongListen songListen = new SongListen();
		if(session != null){
			try{
				flag = sessionService.updateSession(session);
				if(flag > 0){
					message = "Session Updated Successfully";
					songListen.setRating(session.getRating());
					songListen.setUser_id(session.getUserId());
					Song song = new Song();
					song.setId(Integer.parseInt(session.getSession()));
					songListen.setSong(song);
					flag = songServices.addSongListen(songListen);
					if(flag > 0){
						LOG.info("Song Listen Updated");
					}
					else{
						LOG.info("Somg Listen Not Updated");
					}
				}
				else
					message = "Session Can't Be Updated";
			}catch(Exception e){
				message = "Error While Updating Session " + e;
			}
		}
		else{
			message = "Session Object Is Empty";
		}
		LOG.info(message);
		result.setMessage(message);
		result.setId(flag);
		return result;
	}
	
	@RequestMapping(value = "/{userId}/taste", method = RequestMethod.POST)
	@ResponseBody
	public Message setUserTaste(@PathVariable Integer userId, @RequestBody List<Song> songsList){
		Message result = new Message();
		String message = new String();
		Integer id = 0;
		if(songsList != null && userId != null){
			try{
				id = userService.addUserTaste(userId ,songsList);
				
				if(id > 0 ){
					message = "User Taste Updated Successfully";
				}
				else{
					message = "User Taste Can't be Updated";
				}
			}catch(Exception e){
				message = "Error in User Controller " + e;
			}
		}
		else{
			message = "Params are Empty";
		}
		LOG.info(message);
		result.setMessage(message);
		result.setId(id);
		return result;
	}
}