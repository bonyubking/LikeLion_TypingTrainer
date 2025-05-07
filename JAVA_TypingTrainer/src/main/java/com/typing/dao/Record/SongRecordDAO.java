package com.typing.dao.Record;

import java.util.List;

import com.typing.model.dto.SongFilter;
import com.typing.model.dto.SongRecordDTO;


public interface SongRecordDAO {
	
	String getbyfilterSql = 
		    "SELECT r.record_id, r.user_id, u.uid as Uid, "
		    		  + "r.duration, r.correct_count, "
		    		  + "r.played_at, r.genre, r.hint_time "
		    		  + "FROM song_records AS r "
		    		  + "JOIN users AS u ON r.user_id = u.user_id "
		    		  + "WHERE 1=1";
	
	List<SongRecordDTO> selectByFilter(SongFilter filter);

}
