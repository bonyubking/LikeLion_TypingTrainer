package com.typing.service.Record;

import java.util.List;

import com.typing.model.dto.SongFilter;
import com.typing.model.dto.SongRecordDTO;



public interface SongRecordService {
	
    public List<SongRecordDTO> fetchByFilter(SongFilter s);
}
