package com.typing.dao.Record;

import java.util.List;

import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;

public interface TypingRecordDAO {
	
	String getbyfilterSql = 
		    "SELECT r.record_id, r.user_id, u.uid as Uid, u.nickname, "
		    		  + "MINUTE(r.duration) AS duration, r.correct_count, r.typing_speed, r.accuracy, "
		    		  + "r.played_at, r.content_type, r.difficulty, r.language "
		    		  + "FROM typing_records AS r "
		    		  + "JOIN users AS u ON r.user_id = u.user_id "
		    		  + "WHERE 1=1";
	
	
	List<TypingRecordDTO> selectByFilter(TypingFilter filter);
	

}
