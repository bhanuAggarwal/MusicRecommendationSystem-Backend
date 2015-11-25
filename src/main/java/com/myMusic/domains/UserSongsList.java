package com.myMusic.domains;

import java.util.List;

/** 
 * @author bhanu
 *
 */
public class UserSongsList {
	Integer user_id;
	List<Song> songs;
	
	@Override
	public String toString() {
		return "UserSongsList [user_id=" + user_id + ", songs=" + songs + "]";
	}

	public UserSongsList() {
		super();
	}

	public UserSongsList(Integer user_id, List<Song> songs) {
		super();
		this.user_id = user_id;
		this.songs = songs;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	
}
