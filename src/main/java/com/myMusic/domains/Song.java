package com.myMusic.domains;

import java.sql.Timestamp;

/**
 * 
 * @author bhanu
 *
 */
public class Song {
	
	Integer id;
	String name;
	String location;
	Integer category;
	String artist;
	Double rating;
	Integer no_rating;
	String tags;
	Timestamp creation_time;
	
	public Song() {
		super();
	}

	public Song(Integer id, String name, String location, Integer category,
			String artist, Double rating, Integer no_rating ,String tags, Timestamp creation_time) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.category = category;
		this.artist = artist;
		this.rating = rating;
		this.tags = tags;
		this.creation_time = creation_time;
		this.no_rating = no_rating;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", location=" + location
				+ ", category=" + category + ", artist=" + artist + ", rating="
				+ rating + ", no_rating=" + no_rating + ", tags=" + tags
				+ ", creation_time=" + creation_time + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Timestamp getCreation_time() {
		return creation_time;
	}

	public void setCreation_time(Timestamp creation_time) {
		this.creation_time = creation_time;
	}

	public Integer getNo_rating() {
		return no_rating;
	}

	public void setNo_rating(Integer no_rating) {
		this.no_rating = no_rating;
	}
	
}
