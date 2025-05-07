package com.typing.service.Record;

import java.util.List;

import com.typing.dao.Record.TypingRecordDAO;
import com.typing.dao.Record.TypingRecordDAOImpl;
import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;

public class TypingRecordServiceImpl implements TypingRecordService {
    
	private final TypingRecordDAO dao = new TypingRecordDAOImpl();

	@Override
	public List<TypingRecordDTO> fetchByFilter(TypingFilter f) {

        return dao.selectByFilter(f);
	}
}
