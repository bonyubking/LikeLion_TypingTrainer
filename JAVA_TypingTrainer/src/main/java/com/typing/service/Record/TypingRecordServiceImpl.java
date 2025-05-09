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

    @Override
    public void saveGameRecord(TypingRecordDTO gameRecord) {
        // TypingRecordDAO를 사용하여 게임 기록을 저장
        dao.save(gameRecord); // 예시로, save 메서드 구현을 TypingRecordDAO에 추가해야 할 수도 있습니다
    }
}

    
	

