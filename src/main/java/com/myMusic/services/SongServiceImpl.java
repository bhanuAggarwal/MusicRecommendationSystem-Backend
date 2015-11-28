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
		List<Song> temp = new ArrayList<Song>();
		try{
			songsList  = songMapper.getPopularSongs();
			for(int i = 0; i < 9; i++){
				temp.add(i, songsList.get(i));
			}
			if(songsList != null)
				LOG.info("Songs List Retrieved Successfully");
			else
				LOG.info("Error While Fetching Popular Songs List");
		}catch(Exception e){
			LOG.info("Error in Song Services " + e);
		}
		return temp;
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
	public List<Song> getNextRecommendedSongs(Integer userId, Integer songId) {
		List<Song> songsList = new ArrayList<Song>();
		HashMap<String,Integer> songMap = new HashMap<String, Integer>();
		Session previousSession = new Session();
		String sessionString;
		int i = 0;
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
				for(i = 0; i < sessionsList.size(); i++){
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
				i = 0;
				for(i = 0; i < songsSet.size(); i++){
					songsList.add(i, songMapper.getSongById(Integer.parseInt(it.next().toString())));
				}
				if(songId != null)
					songsList.add(i , songMapper.getSongById(songId));
				Collections.reverse(songsList);
				if(songsList.size() < 9){
					try{
						int limit = 9 - songsList.size();
						List<Song> songTemp = songMapper.getSongsByCategory(limit,Integer.parseInt(songsList.get(0).getCategory()));
						if(songTemp != null){
							LOG.info("Song List Generated ");
						}
						songsList.addAll(songTemp);
					}catch(Exception e){
						LOG.info("Error in Songs Service " + e);
					}
				}
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
		UserTaste userTaste = new UserTaste();
		if(userId != null){
			try{
				userTaste = userService.getUserTaste(userId);
				if(userTaste != null){
					List<Song> listTemp1 = songMapper.getSongsByCategory((int)(userTaste.getCategory_1()*100),1);
					if(listTemp1 != null){
						LOG.info("Category " + 1 + " List Generated");
						songsList.addAll(listTemp1);
					}
					else{
						LOG.info("Category "+ 1 +" List Can't be Generated");
					}
					List<Song> listTemp2 = songMapper.getSongsByCategory((int)(userTaste.getCategory_2()*100),2);
					if(listTemp2 != null){
						LOG.info("Category " + 2 + " List Generated");
						songsList.addAll(listTemp2);
					}
					else{
						LOG.info("Category "+ 2 +" List Can't be Generated");
					}
					List<Song> listTemp3 = songMapper.getSongsByCategory((int)(userTaste.getCategory_3()*100),3);
					if(listTemp3 != null){
						LOG.info("Category " + 3 + " List Generated");
						songsList.addAll(listTemp3);
					}
					else{
						LOG.info("Category "+ 3 +" List Can't be Generated");
					}
					List<Song> listTemp4 = songMapper.getSongsByCategory((int)(userTaste.getCategory_4()*100),4);
					if(listTemp4 != null){
						LOG.info("Category " + 4 + " List Generated");
						songsList.addAll(listTemp4);
					}
					else{
						LOG.info("Category "+ 4 +" List Can't be Generated");
					}
					List<Song> listTemp5 = songMapper.getSongsByCategory((int)(userTaste.getCategory_5()*100),5);
					if(listTemp5 != null){
						LOG.info("Category " + 5 + " List Generated");
						songsList.addAll(listTemp5);
					}
					else{
						LOG.info("Category "+ 5 +" List Can't be Generated");
					}
					List<Song> listTemp6 = songMapper.getSongsByCategory((int)(userTaste.getCategory_6()*100),6);
					if(listTemp6 != null){
						LOG.info("Category " + 6 + " List Generated");
						songsList.addAll(listTemp6);
					}
					else{
						LOG.info("Category "+ 6 +" List Can't be Generated");
					}
					List<Song> listTemp7 = songMapper.getSongsByCategory((int)(userTaste.getCategory_7()*100),7);
					if(listTemp7 != null){
						LOG.info("Category " + 7 + " List Generated");
						songsList.addAll(listTemp7);
					}
					else{
						LOG.info("Category "+ 7 +" List Can't be Generated");
					}
					List<Song> listTemp8 = songMapper.getSongsByCategory((int)(userTaste.getCategory_8()*100),8);
					if(listTemp8 != null){
						LOG.info("Category " + 8 + " List Generated");
						songsList.addAll(listTemp8);
					}
					else{
						LOG.info("Category "+ 8 +" List Can't be Generated");
					}
					List<Song> listTemp9 = songMapper.getSongsByCategory((int)(userTaste.getCategory_9()*100),9);
					if(listTemp9 != null){
						LOG.info("Category " + 9 + " List Generated");
						songsList.addAll(listTemp9);
					}
					else{
						LOG.info("Category "+ 9 +" List Can't be Generated");
					}
					List<Song> listTemp10 = songMapper.getSongsByCategory((int)(userTaste.getCategory_10()*100),10);
					if(listTemp10 != null){
						LOG.info("Category " + 10 + " List Generated");
						songsList.addAll(listTemp10);
					}
					else{
						LOG.info("Category "+ 10 +" List Can't be Generated");
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
				if(!userMap.containsKey(list.get(i).getUser_id())){
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
