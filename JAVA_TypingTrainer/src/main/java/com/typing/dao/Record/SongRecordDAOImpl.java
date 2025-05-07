package com.typing.dao.Record;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.typing.model.dto.SongFilter;
import com.typing.model.dto.SongRecordDTO;

import static com.typing.util.DBUtil.sharedConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRecordDAOImpl implements SongRecordDAO {

	@Override
	public List<SongRecordDTO> selectByFilter(SongFilter filter) {
		
		
	    Connection conn = sharedConnection();
	    
        StringBuilder sql = new StringBuilder(SongRecordDAO.getbyfilterSql);
	    
	    List<Object> params = new ArrayList<>();

	    // 3) 동적 필터 조건 추가
	    if (filter.getUserId() != null) {
	        sql.append(" AND r.user_id = ?");
	        params.add(filter.getUserId());
	    }
	    if (filter.getGenre() != null && !filter.getGenre().isEmpty()) {
	        sql.append(" AND genre = ?");
	        params.add(filter.getGenre());
	    }
	    if (filter.getHintTime() != null) {
	        sql.append(" AND hint_time = ?");
	        params.add(filter.getHintTime());
	    }
	    
	    if (filter.getDuration() != null) {
	        sql.append(" AND MINUTE(duration) = ?");
	        params.add(filter.getDuration());
	    }
	    
	    sql.append(" ORDER BY correct_count DESC");
	    

        List<SongRecordDTO> list = new ArrayList<>();
	    
	    
        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	SongRecordDTO dto = new SongRecordDTO();
                    dto.setRecordId(rs.getInt("record_id"));
                    dto.setUserId(rs.getInt("user_id"));
                    dto.setUid(rs.getString("Uid")); 
                    dto.setDuration(rs.getInt("duration"));  
                    dto.setCorrectCount(rs.getInt("correct_count"));
                    dto.setPlayedAt(rs.getTimestamp("played_at").toLocalDateTime().toString());
                    dto.setGenre(rs.getString("genre"));
                    dto.setHintTime(rs.getInt("hint_time"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}