package com.typing.dao.Record;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import static com.typing.util.DBUtil.sharedConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;

public class TypingRecordDAOImpl implements TypingRecordDAO {

	@Override
	public List<TypingRecordDTO> selectByFilter(TypingFilter filter) {
		
		
	    Connection conn = sharedConnection();
	    
        StringBuilder sql = new StringBuilder(TypingRecordDAO.getbyfilterSql);
	    
	    List<Object> params = new ArrayList<>();

	    // 3) 동적 필터 조건 추가
	    if (filter.getUserId() != null) {
	        sql.append(" AND r.user_id = ?");
	        params.add(filter.getUserId());
	    }
	    if (filter.getContentType() != null && !filter.getContentType().isEmpty()) {
	        sql.append(" AND content_type = ?");
	        params.add(filter.getContentType());
	    }
	    if (filter.getDifficulty() != null && !filter.getDifficulty().isEmpty()) {
	        sql.append(" AND difficulty = ?");
	        params.add(filter.getDifficulty());
	    }
	    if (filter.getLanguage() != null && !filter.getLanguage().isEmpty()) {
	        sql.append(" AND language = ?");
	        params.add(filter.getLanguage());
	    }
	    
	    if (filter.getDuration() != null ) {
	        sql.append(" AND MINUTE(duration) = ?");
	        params.add(filter.getDuration());
	    }
	    
	    sql.append(" ORDER BY correct_count DESC");
	    

        List<TypingRecordDTO> list = new ArrayList<>();
	    
	    
        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TypingRecordDTO dto = new TypingRecordDTO();
                    dto.setRecordId(rs.getInt("record_id"));
                    dto.setUserId(rs.getInt("user_id"));
                    dto.setUid(rs.getString("Uid"));
                    dto.setNickname(rs.getString("nickname"));
                    dto.setDuration(rs.getInt("duration")); 
                    dto.setCorrectCount(rs.getInt("correct_count"));
                    dto.setTypingSpeed(rs.getInt("typing_speed"));
                    dto.setAccuracy(rs.getBigDecimal("accuracy"));
                    dto.setPlayedAt(rs.getTimestamp("played_at").toLocalDateTime().toString());
                    dto.setContentType(rs.getString("content_type"));
                    dto.setDifficulty(rs.getString("difficulty"));
                    dto.setLanguage(rs.getString("language"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


@Override
public void save(TypingRecordDTO gameRecord) {
    // 게임 기록을 DB에 저장하는 코드
    String sql = "INSERT INTO typing_records (user_id, correct_count, typing_speed, accuracy, played_at, content_type, difficulty, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = sharedConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, gameRecord.getUserId());
        stmt.setInt(2, gameRecord.getCorrectCount());
        stmt.setInt(3, gameRecord.getTypingSpeed());
        stmt.setBigDecimal(4, gameRecord.getAccuracy());
        stmt.setString(5, gameRecord.getPlayedAt());
        stmt.setString(6, gameRecord.getContentType());
        stmt.setString(7, gameRecord.getDifficulty());
        stmt.setString(8, gameRecord.getLanguage());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("게임 기록 DB 저장 오류", e);
    }
}
}


                       