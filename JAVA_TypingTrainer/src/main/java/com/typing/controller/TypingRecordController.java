package com.typing.controller;

import java.util.List;
import com.typing.model.dto.UserDto; // UserDto import 추가
import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;
import com.typing.service.Record.TypingRecordService;
import com.typing.service.Record.TypingRecordServiceImpl;

public class TypingRecordController {
    
    private final TypingRecordService service = new TypingRecordServiceImpl();
    
    public List<TypingRecordDTO> getByFilter(TypingFilter filter) {
        return service.fetchByFilter(filter);
    }
    
    public void saveGameRecord(UserDto user, TypingRecordDTO gameRecord) {
        if (user != null) {
            // 로그인된 사용자만 게임 기록 저장
            service.saveGameRecord(gameRecord); // service layer에서 기록 저장 처리
        } else {
            System.out.println("비회원은 기록을 저장할 수 없습니다.");
        }
    }
}
