/**
 * 
 */
package com.myMusic.services;

import java.util.List;

import com.myMusic.domains.Song;
import com.myMusic.domains.SongListen;

/**
 * @author bhanu
 *
 */
public interface SongService {

	Integer uploadSong(Song song);
	List<Song> getSampleSongsList();
	Integer addSongListen(SongListen songListen);
	List<Song> getPopularSongs();
	List<Song> getNewSongs();
	List<Song> getNextRecommendedSongs(Integer userId);
	List<Song> getRecommendedSongsForUser(Integer userId);
	Integer updateRatings();
	List<Song> getSongsByDefault();

}
