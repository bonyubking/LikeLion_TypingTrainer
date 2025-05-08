package com.typing.dao.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameSetting;
import com.typing.model.dto.SongRecordDTO;
import com.typing.util.DBUtil;

public class SongDaoImpl implements SongDao {

	@Override
	public List<SongDto> getRandomSongsByGenre(SongGameSetting setting) {
	    List<SongDto> result = new ArrayList<>();
	    List<String> genres = setting.getGenre();
	    List<Integer> excludeIds = setting.getExcludedSongIds();

	    if (genres.isEmpty()) return result;

	    StringBuilder query = new StringBuilder("SELECT * FROM songs WHERE genre IN (");
	    query.append(String.join(",", genres.stream().map(g -> "?").toArray(String[]::new)));
	    query.append(")");

	    // 문제 중복 예외 처리
	    if (!excludeIds.isEmpty()) {
	        query.append(" AND id NOT IN (");
	        query.append(String.join(",", excludeIds.stream().map(id -> "?").toArray(String[]::new)));
	        query.append(")");
	    }

	    query.append(" ORDER BY RAND() LIMIT 1");

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query.toString())) {

	        int paramIndex = 1;

	        for (String genre : genres) {
	            stmt.setString(paramIndex++, genre);
	        }

	        for (Integer id : excludeIds) {
	            stmt.setInt(paramIndex++, id);
	        }

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            SongDto song = new SongDto();
	            song.setId(rs.getInt("song_id"));
	            song.setTitle(rs.getString("title"));
	            song.setLyrics(rs.getString("lyrics"));
	            song.setGenre(rs.getString("genre"));
	            song.setInitial(rs.getString("initial"));
	            song.setSinger(rs.getString("singer"));
	            result.add(song);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	@Override
	public void saveRecord(SongRecordDTO record) {
	    String sql = "INSERT INTO Song_records (user_id, duration, correct_count, played_at, genre, hint_time) "
	               + "VALUES (?, SEC_TO_TIME(?), ?, ?, ?, ?)";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, record.getUserId());
	        stmt.setInt(2, record.getDuration());
	        stmt.setInt(3, record.getCorrectCount());
	        stmt.setString(4, record.getPlayedAt());
	        stmt.setString(5, record.getGenre());
	        stmt.setInt(6, record.getHintTime());

	        int result = stmt.executeUpdate();
	        if (result > 0) {
	            System.out.println("Game Record Saved");
	        } else {
	            System.out.println("Game Record Unsaved : maybe got some error");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
