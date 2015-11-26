/**
 * 
 */
package com.myMusic.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.myMusic.domains.Song;
import com.myMusic.domains.SongListen;

/**
 * @author bhanu
 *
 */

public interface SongMapper {
	
	@Insert("INSERT INTO songs(name,location,category,artist,rating,tags) "
			+ "VALUES(#{name},#{location},#{category},#{artist},#{rating},#{tags})")
	Integer uploadSong(Song song);

	@Select("SELECT s.id, s.name, s.location, s.category, s.artist, s.rating"
			+ " FROM songs s JOIN sample_songs ss ON s.id = ss.song_id")
	List<Song> getSampleSongsList();
	
	@Insert("INSERT INTO song_listen(user_id, song_id, rating) "
			+ "VALUES(#{user_id}, #{song.id}, #{rating})")
	Integer addSongListen(SongListen songListen);
	
	@Select("SELECT * FROM songs ORDER BY rating DESC LIMIT 15")
	List<Song> getPopularSongs();

	@Select("SELECT * FROM songs ORDER BY creation_time DESC LIMIT 15")
	List<Song> getNewSongs();
	
	//TODO: Map columns To object
	@Select("SELECT sl.id , sl.user_id, sl.rating , s.category AS \" song.category\", "
			+ "s.artist AS \" song.artist\" , s.id AS \"song.id\" "
			+ " FROM song_listen sl JOIN songs s ON sl.song_id = s.id")
	List<SongListen> getSongsListen();

	@Update("UPDATE songs SET rating = #{rating}, no_rating = no_rating + 1 WHERE id = #{id}")
	Integer updateSongRating(Song song);

	@Select("SELECT * FROM songs WHERE id = #{songId}")
	Song getSongById(Integer songId);
	
	@Select("SELECT * FROM songs")
	List<Song> getSongsByDefault();

	@Select("SELECT * FROM songs WHERE category = #{category} ORDER BY rating DESC LIMIT #{limitCategory_1} ")
	List<Song> getSongsByCategory(@Param("limitCategory_1") Integer limitCategory_1,
			@Param("category") Integer category);

	@Delete("DELETE FROM song_listen WHERE 1")
	Integer clearSongListen();
	

}
