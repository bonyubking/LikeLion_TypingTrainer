package com.typing.service.User;

import com.typing.model.dto.UserDto;

public interface UserService {
	void signup(UserDto dto); // 회원가입
	String findByUid(String uid); // ID 이용한 사용자 조회 
}
