package com.typing.service.User;

import com.typing.dao.User.UserDao;
import com.typing.dao.User.UserDaoImpl;
import com.typing.model.dto.UserDto;
import com.typing.model.entity.User;

public class UserServiceImpl implements UserService{
	private final UserDao userDao = new UserDaoImpl();
	@Override
	public void signup(UserDto dto) { // 회원가입
		
		if(findByUid(dto.getuId()) == null) { // 이미 저장된 ID인지 다시 확인. 있을 경우 예외처리  
			User user = new User(dto.getuId(),dto.getPassword(),dto.getNickname());
			userDao.save(user);
		}else { 
			throw new RuntimeException("이미 존재하는 아이디입니다.");
		}
	}
	@Override
	public UserDto login(UserDto dto) { // 로그인 
		UserDto user = findByUid(dto.getuId());
		if(user != null && dto.getPassword().equals(user.getPassword())){
			return user;
		}else {
			throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
	}
	@Override
	public UserDto findByUid(String uid) {// 저장된 ID인지 확인
		return userDao.findByUid(uid);
	}
	@Override
	public boolean checkNickname(String nickname) {// 닉네임 중복 확인
		return userDao.checkNickname(nickname) == null ? true:false;
	}
}
