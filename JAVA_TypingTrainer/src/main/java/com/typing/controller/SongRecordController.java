package com.typing.controller;

import java.util.List;

import com.typing.model.dto.SongFilter;
import com.typing.model.dto.SongRecordDTO;
import com.typing.service.Record.SongRecordService;
import com.typing.service.Record.SongRecordServiceImpl;

public class SongRecordController {
    
	private final SongRecordService service = new SongRecordServiceImpl();
    
    public List<SongRecordDTO> getByFilter(SongFilter filter) {
        return service.fetchByFilter(filter);
    }
}
