/**
 * 
 */
package com.myMusic.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.myMusic.domains.User;

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
	@Insert("INSERT INTO user(name, email,password)"
            + " VALUES (#{name},#{email}, #{password})")
	public Integer createUser(User user);
	
	@Select("Select id from user WHERE email = #{email}")
	public Integer getUserByEmail(User user);
	

}
