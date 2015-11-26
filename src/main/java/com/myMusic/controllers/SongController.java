/**
 * 
 */
package com.myMusic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myMusic.domains.Message;
import com.myMusic.domains.Song;
import com.myMusic.services.SongService;

/**
 * @author bhanu
 *
 */
@Controller
@RequestMapping("/song")
public class SongController {

	Logger LOG = Logger.getLogger("SongController");
	
	@Autowired
	SongService songService;
	
	/**
	 * Get List of Popular Songs
	 * @return
	 */
	@RequestMapping(value = "{type}", method = RequestMethod.GET)
	@ResponseBody
	public List<Song> getPopularSongs(@PathVariable String type,
			@RequestParam(value = "userId" , required = false) Integer userId){
		List<Song> list = new ArrayList<Song>();
		if(type != null){
			try{
				if(type.equals("popular")){
					list = songService.getPopularSongs();
				}
				else if(type.equals("new")){
					list = songService.getNewSongs();
				}
				else if(type.equals("nextRecommended")){
					list = songService.getNextRecommendedSongs(userId);
				}
				else if(type.equals("recommendedForUser")){
					list = songService.getRecommendedSongsForUser(userId);
				}
				else if(type.equals("default")){
					list = songService.getSongsByDefault();
				}
				else{
					LOG.info("Wrong Type Specified");
				}
			}catch(Exception e){
				LOG.info("Error in Song Controller " + e);
			}
		}
		else{
			LOG.info("Some Param Missing");
		}
		return list;
	}
	
	/**
	 * This will upload the Song on the Server
	 * @param song
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Message uploadSong(@RequestBody Song song){
		Message result = new Message();
		String message = new String();
		Integer flag  = 0;
		if(song != null){
			try{
				flag = songService.uploadSong(song);
				if(flag > 0)
					message = "Song Uploaded Successfully";
				else
					message = "Song Can't be Uploaded";
			}catch(Exception e){
				message = "Error in Song Controller " + e;
			}
		}
		else{
			message = "Song Variable is Empty";
		}
		LOG.info(message);
		result.setMessage(message);
		return result;
	}
	/**
	 * This will  
	 * @return
	 */
	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	@ResponseBody
	public List<Song> getSampleSongsList(){
		List<Song> sampleList = new ArrayList<Song>();
		try{
			sampleList = songService.getSampleSongsList();
		}catch(Exception e){
			LOG.info("Error in In Getting Sample Songs " + e);
		}
		return sampleList;
	}
	/**This will Update Songs' Rating
	 * 
	 * @return
	 */
	@RequestMapping(value = "/rating", method = RequestMethod.PUT)
	@ResponseBody
	public Message updateSongsRating(){
		Message result = new Message();
		String message = new String();
		Integer id = 0;
		try{
			id = songService.updateRatings();
			if(id > 0){
				message = "Songs Ratings Updated";
			}
			else{
				message = "Songs Rating Not Updated Successfully";
			}
		}catch(Exception e){
			message = "Error in Song Controller " + e;
		}
		result.setId(id);
		result.setMessage(message);
		return result;
	}
}
