package com.typing.controller;

import java.util.List;

import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;
import com.typing.service.Record.TypingRecordService;
import com.typing.service.Record.TypingRecordServiceImpl;

public class TypingRecordController {
    
	private final TypingRecordService service = new TypingRecordServiceImpl();
    
    public List<TypingRecordDTO> getByFilter(TypingFilter filter) {
        return service.fetchByFilter(filter);
    }
}
