package com.typing.service.Record;

import java.util.List;

import com.typing.dao.Record.SongRecordDAOImpl;
import com.typing.model.dto.SongFilter;
import com.typing.model.dto.SongRecordDTO;
import com.typing.dao.Record.SongRecordDAO;



public class SongRecordServiceImpl implements SongRecordService {
    
	private final SongRecordDAO dao = new SongRecordDAOImpl();


	@Override
	public List<SongRecordDTO> fetchByFilter(SongFilter s) {

        return dao.selectByFilter(s);
	}
}
