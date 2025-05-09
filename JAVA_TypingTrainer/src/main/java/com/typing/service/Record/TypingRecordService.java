package com.typing.service.Record;

import java.util.List;

import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;

public interface TypingRecordService {
	
    public List<TypingRecordDTO> fetchByFilter(TypingFilter f);

	public void saveGameRecord(TypingRecordDTO gameRecord);

}
