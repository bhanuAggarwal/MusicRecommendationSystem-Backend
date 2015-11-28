/**
 * 
 */
package com.myMusic.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMusic.domains.Song;
import com.myMusic.domains.User;
import com.myMusic.domains.UserTaste;
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
					userId = userMapper.getUserByEmail(user).getId();
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
	public Integer validateLogin(User user) {
		User originalUser;
		Integer userId = 0;
		if(user != null){
			try{
				originalUser = userMapper.getUserByEmail(user);
				if(originalUser != null){
					if(originalUser.getPassword().equals(user.getPassword())){
						userId = originalUser.getId();
					}
					else{
						userId = -1;
					}
				}
				else{
					userId = 0;
				}
			}catch(Exception e){
				LOG.info("Error While Getting User : " + e);
			}
		}
		else{
			LOG.info("User is NULL");
		}
		return userId;
	}

	@Override
	public Integer addUserTaste(Integer userId, List<Song> songsList) {
		Integer flag = 0;
		UserTaste userTaste = new UserTaste();
		if(userId != null && songsList != null){
			for(int i = 0; i < songsList.size(); i++){
				songsList.get(i).setCategory(userMapper.getCategoryByName(songsList.get(i).getCategory()).toString());
			}
			userTaste = setUserTasteObject(userId, songsList);
			try{
				flag = userMapper.addUserTaste(userTaste);
			}catch(Exception e){
				LOG.info("Error in User Service " + e);
			}
		}
		else{
			LOG.info("Param is Empty");
		}
		return flag;
	}

	@Override
	public Integer updateUserTaste(Integer userId, List<Song> songsList) {
		Integer flag = 0;
		UserTaste userTaste = new UserTaste();
		UserTaste previousUserTaste = new UserTaste();
		Double rating;
		if(userId != null && songsList != null){
			try{
				previousUserTaste = userMapper.getUserTaste(userId);
				userTaste = setUserTasteObject(userId, songsList);
				
				//Update User's Taste By Taking Avg of Previous and New Taste
				//Category_1
				rating = previousUserTaste.getCategory_1()*previousUserTaste.getCount();
				rating += userTaste.getCategory_1()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_1(rating);
				
				//Category_2
				rating = previousUserTaste.getCategory_2()*previousUserTaste.getCount();
				rating += userTaste.getCategory_2()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_2(rating);
				
				//Category_3
				rating = previousUserTaste.getCategory_3()*previousUserTaste.getCount();
				rating += userTaste.getCategory_3()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_3(rating);
				
				//Category_4
				rating = previousUserTaste.getCategory_4()*previousUserTaste.getCount();
				rating += userTaste.getCategory_4()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_4(rating);
				
				//Category_5
				rating = previousUserTaste.getCategory_5()*previousUserTaste.getCount();
				rating += userTaste.getCategory_5()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_5(rating);
				
				//Category_6
				rating = previousUserTaste.getCategory_6()*previousUserTaste.getCount();
				rating += userTaste.getCategory_6()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_6(rating);
				
				//Category_7
				rating = previousUserTaste.getCategory_7()*previousUserTaste.getCount();
				rating += userTaste.getCategory_7()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_7(rating);
				
				//Category_8
				rating = previousUserTaste.getCategory_8()*previousUserTaste.getCount();
				rating += userTaste.getCategory_8()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_8(rating);
				
				//Category_9
				rating = previousUserTaste.getCategory_9()*previousUserTaste.getCount();
				rating += userTaste.getCategory_9()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_9(rating);
				
				//Category_10
				rating = previousUserTaste.getCategory_10()*previousUserTaste.getCount();
				rating += userTaste.getCategory_10()*userTaste.getCount();
				rating /= previousUserTaste.getCount() + userTaste.getCount();
				userTaste.setCategory_10(rating);
				
				if(userTaste != null)
					flag = 1;
			}catch(Exception e){
				LOG.info("Error While Updating User Taste " + e);
			}
		}
		else{
			LOG.info("Some Param Missing");
		}
		return flag;
	}
	
	private UserTaste setUserTasteObject(Integer userId, List<Song> songsList){
		UserTaste userTaste = new UserTaste();
		Double totalRatings = new Double(0);
		if(songsList != null && userId != null){
			userTaste.setUser_id(userId);	
			userTaste.setCategory_1(0.0);
			userTaste.setCategory_2(0.0);
			userTaste.setCategory_3(0.0);
			userTaste.setCategory_4(0.0);
			userTaste.setCategory_5(0.0);
			userTaste.setCategory_6(0.0);
			userTaste.setCategory_7(0.0);
			userTaste.setCategory_8(0.0);
			userTaste.setCategory_9(0.0);
			userTaste.setCategory_10(0.0);
			userTaste.setCount(0);
				for(int i = 0; i < songsList.size(); i++){
					totalRatings += songsList.get(i).getRating();
					userTaste.setCount(userTaste.getCount() + 1);
					switch(Integer.parseInt(songsList.get(i).getCategory())){
						case 1:
							userTaste.setCategory_1(songsList.get(i).getRating() + userTaste.getCategory_1());
							break;
						case 2:
							userTaste.setCategory_2(songsList.get(i).getRating() + userTaste.getCategory_2());
							break;
						case 3:
							userTaste.setCategory_3(songsList.get(i).getRating() + userTaste.getCategory_3());
							break;
						case 4:
							userTaste.setCategory_4(songsList.get(i).getRating() + userTaste.getCategory_4());
							break;
						case 5:
							userTaste.setCategory_5(songsList.get(i).getRating() + userTaste.getCategory_5());
							break;
						case 6:
							userTaste.setCategory_6(songsList.get(i).getRating() + userTaste.getCategory_6());
							break;
						case 7:
							userTaste.setCategory_7(songsList.get(i).getRating() + userTaste.getCategory_7());
							break;
						case 8:
							userTaste.setCategory_8(songsList.get(i).getRating() + userTaste.getCategory_8());
							break;
						case 9:
							userTaste.setCategory_9(songsList.get(i).getRating() + userTaste.getCategory_9());
							break;
						case 10:
							userTaste.setCategory_10(songsList.get(i).getRating() + userTaste.getCategory_10());
							break;
					}
				}
				userTaste.setCategory_1((userTaste.getCategory_1()/totalRatings));
				userTaste.setCategory_2((userTaste.getCategory_2()/totalRatings));
				userTaste.setCategory_3((userTaste.getCategory_3()/totalRatings));
				userTaste.setCategory_4((userTaste.getCategory_4()/totalRatings));
				userTaste.setCategory_5((userTaste.getCategory_5()/totalRatings));
				userTaste.setCategory_6((userTaste.getCategory_6()/totalRatings));
				userTaste.setCategory_7((userTaste.getCategory_7()/totalRatings));
				userTaste.setCategory_8((userTaste.getCategory_8()/totalRatings));
				userTaste.setCategory_9((userTaste.getCategory_9()/totalRatings));
				userTaste.setCategory_10((userTaste.getCategory_10()/totalRatings));
		}
		else{
			LOG.info("Some Param Missing");
		}
		return userTaste;
	}

	@Override
	public UserTaste getUserTaste(Integer userId) {
		UserTaste userTaste = new UserTaste();
		if(userId != null){
			userTaste = userMapper.getUserTaste(userId);
		}
		else{
			LOG.info("Params are Missing");
		}
		return userTaste;
	}
	
	
}

