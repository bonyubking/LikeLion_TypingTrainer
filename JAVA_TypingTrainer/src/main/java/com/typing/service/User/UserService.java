package com.typing.service.User;

import com.typing.model.dto.UserDto;
import com.typing.model.entity.User;

public interface UserService {
	void signup(UserDto dto); // 회원가입
	UserDto login(UserDto dto); // 로그인
	UserDto findByUid(String uid); // ID 이용한 사용자 조회 
	boolean checkNickname(String nickname); // Nickname 이용한 사용자 조회
}
