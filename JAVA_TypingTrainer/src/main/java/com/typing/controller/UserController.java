package com.typing.controller;

import com.typing.model.dto.UserDto;
import com.typing.service.User.UserService;
import com.typing.service.User.UserServiceImpl;
import com.typing.model.dto.TypingRecordDTO;

public class UserController {
    private final UserService userService = new UserServiceImpl();
    private final TypingRecordController typingRecordController = new TypingRecordController(); // TypingRecordController는 따로 선언합니다.
    
    public void signup(UserDto dto) { // 회원가입
        userService.signup(dto);
    }

    public UserDto login(UserDto dto) { // 로그인
        return userService.login(dto);
    }

    public boolean checkNickname(String nickname) { // 닉네임 중복 확인
        return userService.checkNickname(nickname);
    }

    public boolean checkUid(String uid) { // 아이디 중복 확인
        return userService.findByUid(uid) == null ? true : false;
    }

    // 게임 종료 후 기록 저장
    public void handleGameEnd(UserDto user, TypingRecordDTO gameRecord) {
        if (user != null) {
            // 로그인한 사용자
            typingRecordController.saveGameRecord(user, gameRecord); // 게임 기록 저장
        } else {
            // 비회원
            System.out.println("비회원으로 게임 기록이 저장되지 않습니다.");
        }
    }
}
