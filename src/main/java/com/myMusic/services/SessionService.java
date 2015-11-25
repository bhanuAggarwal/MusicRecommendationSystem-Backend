/**
 * 
 */
package com.myMusic.services;

import java.util.List;

import com.myMusic.domains.Session;

/**
 * @author bhanu
 *
 */
public interface SessionService {
	
	public Integer updateSession(Session session);

	public Integer startSession(Integer userId);

	public Session getUserCurrentSession(Integer userId);

	public List<String> getSimilarSessions(String sessionString);
}
