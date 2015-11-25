/**
 * 
 */
package com.myMusic.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.myMusic.domains.Session;

/**
 * @author bhanu
 *
 */
public interface SessionMapper {
	/**
	 * This Will Start a New Session
	 * @param userId
	 * @return
	 */
	@Insert("INSERT INTO sessions(user_id,session) VALUES(#{userId},'')")
	public Integer startSession(Integer userId);
	
	/**
	 * This will return User's Current Session
	 * @param userId
	 * @return
	 */
	@Select("SELECT id , user_id AS \"userId\", session FROM sessions WHERE user_id = #{userId} ORDER BY id DESC LIMIT 1")
	public Session getUserCurrentSession(Integer userId);
	
	/**
	 * This will Update the Session
	 * @param session
	 * @return
	 */
	@Update("UPDATE sessions SET session = #{session} WHERE id = #{id}")
	public Integer updateSession(Session session);
	
	/**
	 * Get Similar Sessions With the Current Sessions
	 * @param sessionString
	 * @return
	 */
	@Select("SELECT session FROM sessions WHERE session LIKE #{sessionString}")
	public List<String> getSimilarSessions(String sessionString);

	
}
