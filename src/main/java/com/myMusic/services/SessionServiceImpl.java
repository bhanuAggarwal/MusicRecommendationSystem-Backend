package com.myMusic.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMusic.domains.Session;
import com.myMusic.mappers.SessionMapper;

/**
 * 
 * @author bhanu
 *
 */
@Service
public class SessionServiceImpl implements SessionService{
	
	@Autowired
	SessionMapper sessionMapper;
	
	Logger LOG = Logger.getLogger("SessionService");
	
	@Override
	public Integer startSession(Integer userId) {
		Integer sessionFlag = 0;
		if(userId != null){
			sessionFlag = sessionMapper.startSession(userId);
			if(sessionFlag > 0){
				LOG.info("Session Started Successfully");
			}
			else{
				LOG.info("Error While Starting Session");
			}
		}
		else{
			LOG.info("UserId is null");
		}
		return sessionFlag;
	}

	@Override
	public Integer updateSession(Session session) {
		Integer flag = 0;
		Session currentSession;
		if(session != null){
			try{
				currentSession = sessionMapper.getUserCurrentSession(session.getUserId());
				if(currentSession.getSession().charAt(currentSession.getSession().length() - 1) == session.getSession().charAt(0)){
					return 1;
				}
				session.setId(currentSession.getId());
				if(!currentSession.getSession().equals("")){
					session.setSession(currentSession.getSession() + "," + session.getSession());
				}
				flag = sessionMapper.updateSession(session);
			}catch(Exception e){
				LOG.info("Error While Updating Session in Service " + e);
			}
		}
		else{
			LOG.info("Session Object is null");
		}
		return flag;
	}
	@Override
	public Session getUserCurrentSession(Integer userId) {
		Session session = new Session();
		if(userId != null){
			try{
				session = sessionMapper.getUserCurrentSession(userId);
				if(session != null){
					
				}
				else{
					LOG.info("Session Variable not Set");
				}
			}catch(Exception e){
				LOG.info("Error in Session Services " + e);
			}
		}
		else{
			LOG.info("Params are Missing");
		}
		return session;
	}

	@Override
	public List<String> getSimilarSessions(String sessionString) {
		List<String> sessionsList = new ArrayList<String>();
		if(sessionString != null){
			try{
				sessionsList = sessionMapper.getSimilarSessions(sessionString);
				if(sessionsList != null){
					LOG.info("Session List Generated");
				}
				else{
					LOG.info("Session List Not Generated");
				}
			}catch(Exception e){
				LOG.info("Error in Session Services " + e);
			}
		}
		else{
			LOG.info("Some Param Missing");
		}
		return sessionsList;
	}
	
}
