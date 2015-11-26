/**
 * 
 */
package com.myMusic.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myMusic.domains.Session;
import com.myMusic.domains.Song;
import com.myMusic.domains.SongListen;
import com.myMusic.domains.UserTaste;
import com.myMusic.mappers.SongMapper;

/**
 * @author bhanu
 *
 */
@Service
public class SongServiceImpl implements SongService{
	
	Logger LOG = Logger.getLogger("SongService");
	
	@Autowired
	SongMapper songMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService sessionService;
	
	@Override
	public Integer uploadSong(Song song) {
		Integer flag = 0;
		if(song != null){
			try{
				flag = songMapper.uploadSong(song);
				if(flag > 0){
					LOG.info("Song Uploaded Succcessfully");
				}
				else{
					LOG.info("Song Can't be Uploaded");
				}
			}catch(Exception e){
				LOG.info("Error In Song Services " + e);
			}
		}
		else{
			LOG.info("Song Variable Empty");
		}
		return flag;
	}

	@Override
	public List<Song> getSampleSongsList() {
		List<Song> songList = new ArrayList<Song>();
		try{
			songList = songMapper.getSampleSongsList();
			if(songList == null){
				LOG.info("Sample Song List is NULL");
			}
			else{
				LOG.info("Sample Song List Retrieved Successfully");
			}
		}catch(Exception e){
			LOG.info("Error in Sample Service " + e);
		}
		return songList;
	}

	@Override
	public Integer addSongListen(SongListen songListen) {
		Integer flag = 0;
		if(songListen != null){
			try{
				flag = songMapper.addSongListen(songListen);
				if(flag > 0){
					LOG.info("Song Listen Updated");
				}
				else{
					LOG.info("Somg Listen Not Updated");
				}
			}catch(Exception e){
				LOG.info("Error While Adding Listen Song " + e);
			}
		}
		else{
			LOG.info("Song Param is Empty");
		}
		return flag;
	}

	@Override
	public List<Song> getPopularSongs() {
		List<Song> songsList = new ArrayList<Song>();
		try{
			songsList  = songMapper.getPopularSongs();
			if(songsList != null)
				LOG.info("Songs List Retrieved Successfully");
			else
				LOG.info("Error While Fetching Popular Songs List");
		}catch(Exception e){
			LOG.info("Error in Song Services " + e);
		}
		return songsList;
	}

	@Override
	public List<Song> getNewSongs() {
		List<Song> songsList = new ArrayList<Song>();
		try{
			songsList = songMapper.getNewSongs();
			if(songsList != null)
				LOG.info("Songs List Retrieved Successfully");
			else
				LOG.info("Error While Fetching Popular Songs List");
		}catch(Exception e){
			LOG.info("Error in Song Service " + e);
		}
		return songsList;
	}

	@Override
	public List<Song> getNextRecommendedSongs(Integer userId) {
		List<Song> songsList = new ArrayList<Song>();
		HashMap<String,Integer> songMap = new HashMap<String, Integer>();
		Session previousSession = new Session();
		String sessionString;
		List<String> sessionsList = new ArrayList<String>();
		if(userId != null){
			try{
				previousSession = sessionService.getUserCurrentSession(userId);
				sessionString = "%" + previousSession.getSession() + ",%";
				sessionsList = sessionService.getSimilarSessions(sessionString);
				while(sessionsList.size() == 0 && previousSession.getSession().length() > 1){
					int length = previousSession.getSession().length();
					previousSession.setSession(previousSession.getSession().substring(length/2));
					if(previousSession.getSession().charAt(0) == ','){
						previousSession.setSession(previousSession.getSession().substring(1));
					}
					sessionString = "%" + previousSession.getSession() + ",%";
					sessionsList = sessionService.getSimilarSessions(sessionString);
				}
				for(int i = 0; i < sessionsList.size(); i++){
					String[] sessionTemp = sessionsList.get(i).split(previousSession.getSession() + ",");
					sessionsList.set(i, sessionTemp[1]);
					sessionTemp = sessionsList.get(i).split(",");
					if(songMap.containsKey(sessionTemp[0])){
						songMap.put(sessionTemp[0], songMap.get(sessionTemp[0]) + 1);
					}
					else{
						songMap.put(sessionTemp[0],1);
					}
				}
				Map<String,Integer> Map = sortByComparator(songMap);
				Set<String> songsSet = Map.keySet();
				Iterator<String> it = songsSet.iterator();
				for(int i = 0; i < songsSet.size(); i++){
					songsList.add(i, songMapper.getSongById(Integer.parseInt(it.next().toString())));
				}	
				Collections.reverse(songsList);
			}catch(Exception e){
				LOG.info("Error in Song Services " + e);
			}
		}
		else{
			LOG.info("Params are Missing");
		}
		return songsList;
	}

	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = 
			new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
                                           Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	@Override
	public List<Song> getRecommendedSongsForUser(Integer userId) {
		List<Song> songsList = new ArrayList<Song>();
		List<Song> listTemp = new ArrayList<Song>();
		UserTaste userTaste = new UserTaste();;
		if(userId != null){
			try{
				userTaste = userService.getUserTaste(userId);
				if(userTaste != null){
					for(int i = 1; i <= 10; i++){
						listTemp = songMapper.getSongsByCategory((int)(userTaste.getCategory_1()*100),i);
						if(listTemp != null){
							LOG.info("Category " + i + " List Generated");
							songsList.addAll(listTemp);
						}
						else{
							LOG.info("Category "+ i +" List Can't be Generated");
						}
					}
					Collections.sort(songsList,new SongComparator());
				}
				else{
					LOG.info("User Taste is Null");
					return null;
				}
			}catch(Exception e){
				LOG.info("Error in Song Services " + e);
			}
		}
		else{
			LOG.info("Params are null");
		}
		return songsList;
	}
	
	@Override
	public List<Song> getSongsByDefault() {
		List<Song> songList = new ArrayList<Song>();
		try{
			songList = songMapper.getSongsByDefault();
			if(songList != null){
				LOG.info("Song List Generated Succcessfully");
			}
			else{
				LOG.info("Songs List Can't be Generated");
			}
		}catch(Exception e){
			LOG.info("Error in Song Services " + e);
		}
		return songList;
	}

	@Override
	public Integer updateRatings() {
		Integer id = 0,flagCount = 0;
		List<SongListen> list = new ArrayList<SongListen>();
		List<Integer> userIdList = new ArrayList<Integer>(); 
		HashMap<Integer, List<Song>> userMap = new HashMap<Integer, List<Song>>();
		try{
			list = songMapper.getSongsListen();
			for(int i = 0; i < list.size(); i++){
				if(userMap.containsKey(list.get(i).getUser_id())){
					userMap.put(list.get(i).getUser_id(), new ArrayList<Song>());
					userIdList.add(list.get(i).getUser_id());
				}
				userMap.get(list.get(i).getUser_id()).add(list.get(i).getSong());
			}
			
			//Update Songs Ratings In the Global Table
			updateSongsRating(list);
			
			//Update User's Song Taste
			for(int i = 0; i < userIdList.size(); i++){
				int temp = userService.updateUserTaste(userIdList.get(i), userMap.get(userIdList.get(i)));
				if(temp > 0)
					flagCount++;
			}
			
			if(flagCount.equals(userIdList.size()))
				id = 1;
			else
				id = 0;
			
			//Empty Song Listen Table
			songMapper.clearSongListen();
		}catch(Exception e){
			LOG.info("Error in Song Service " + e);
		}
		return id;
	}
	
	private Integer updateSongsRating(List<SongListen> list){
		Integer flag = 0;
		Double rating;
		Song songTemp;
		if(list != null){
			try{
				for(int i = 0; i < list.size(); i++){
					songTemp = list.get(i).getSong();
					rating = songTemp.getRating() * songTemp.getNo_rating() + list.get(i).getRating();
					rating /= (songTemp.getNo_rating() + 1);
					songTemp.setRating(rating);
					int songFlag = songMapper.updateSongRating(songTemp);
					if(songFlag > 0){
						flag++;
						LOG.info(list.get(i).getSong().getName() + " Updated ");
					}
					else{
						LOG.info(list.get(i).getSong().getName() + " Not Updated ");
					}
				}
				if(flag.equals(list.size())){
					flag = 1;
					LOG.info("All Songs Updated Successfully");
				}
				else{
					flag = 0;
					LOG.info("All Songs Not Updated");
				}
			}catch(Exception e){
				LOG.info("Error while Updating Song's Rating " + e );
			}
		}
		else{
			LOG.info("List Is Empty");
		}
		
		return flag;
	}
}

class SongComparator implements Comparator<Song>{
	
    @Override
    public int compare(Song s1, Song s2) {
        if(s1.getRating() < s2.getRating()){
            return 1;
        } else {
            return -1;
        }
    }
}
