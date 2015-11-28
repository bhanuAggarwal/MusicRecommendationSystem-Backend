/**
 * 
 */
package com.myMusic.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.myMusic.domains.User;
import com.myMusic.domains.UserTaste;

/**
 * @author bhanu
 *
 */
public interface UserMapper {
	
	/**
	 * create a user
	 * 
	 * @param user
	 * @return
	 */
	//@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert("INSERT INTO user(name, email,password,creation_time)"
            + " VALUES (#{name},#{email}, #{password},now())")
	public Integer createUser(User user);
	
	@Select("SELECT * FROM user WHERE email = #{email}")
	public User getUserByEmail(User user);
	
	@Insert("INSERT INTO user_taste(user_id, category_1, category_2, category_3, category_4, category_5 "
			+ ", category_6, category_7, category_8, category_9, category_10, count) "
			+ "VALUES(#{user_id}, #{category_1}, #{category_2}, #{category_3}, #{category_4},  #{category_5}, "
			+ " #{category_6}, #{category_7}, #{category_8}, #{category_9}, #{category_10}, #{count})")
	public Integer addUserTaste(UserTaste userTaste);
	
	@Select("SELECT * FROM user_taste WHERE user_id = #{userId}")
	public UserTaste getUserTaste(Integer userId);
	
	@Select("SELECT id FROM category_master WHERE name = #{category}")
	public Integer getCategoryByName(String category);
}
