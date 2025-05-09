package com.typing.service.Record;

import java.util.List;

import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;

public interface TypingRecordService {
	
	void saveGameRecord(TypingRecordDTO gameRecord);
    public List<TypingRecordDTO> fetchByFilter(TypingFilter f);
}
