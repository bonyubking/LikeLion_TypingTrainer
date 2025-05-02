package com.typing.controller;

import com.typing.model.dto.UserDto;
import com.typing.service.User.UserService;
import com.typing.service.User.UserServiceImpl;

public class UserController {
	private final UserService userService = new UserServiceImpl();
	
	public void signup(UserDto dto) {// 회원가입
		userService.signup(dto);
	}
	public UserDto login(UserDto dto) {// 로그인 
		return userService.login(dto);
	}
	public boolean checkNickname(String nickname) {
		return userService.checkNickname(nickname);
	}
}
