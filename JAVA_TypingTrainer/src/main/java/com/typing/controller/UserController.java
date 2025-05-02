package com.typing.controller;

import com.typing.model.dto.UserDto;
import com.typing.service.User.UserService;
import com.typing.service.User.UserServiceImpl;

public class UserController {
	private final UserService userService = new UserServiceImpl();
	
	public void signup(UserDto dto) {// 회원가입
		userService.signup(dto);
	}
}
