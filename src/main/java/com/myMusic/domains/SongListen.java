/**
 * 
 */
package com.myMusic.domains;

/**
 * @author bhanu
 *
 */
public class SongListen {
	Integer id;
	Integer user_id;
	Song song;
	Double rating;
	
	@Override
	public String toString() {
		return "SongListen [id=" + id + ", user_id=" + user_id + ", song="
				+ song + ", rating=" + rating + "]";
	}

	public SongListen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SongListen(Integer id, Integer user_id, Song song, Double rating) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.song = song;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	
}
