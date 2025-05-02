package com.typing.service.User;

import com.typing.dao.User.UserDao;
import com.typing.dao.User.UserDaoImpl;
import com.typing.model.dto.UserDto;
import com.typing.model.entity.User;

public class UserServiceImpl implements UserService{
	private final UserDao userDao = new UserDaoImpl();
	@Override
	public void signup(UserDto dto) {
		
		if(findByUid(dto.getuId()) == null) { // 이미 저장된 ID인지 다시 확인. 있을 경우 예외처리  
			User user = new User(dto.getuId(),dto.getPassword(),dto.getNickname());
			userDao.save(user);
		}else { 
			throw new RuntimeException("이미 존재하는 아이디입니다.");
		}
	}
	@Override
	public String findByUid(String uid) {// 저장된 ID인지 확인
		return userDao.findByUid(uid);
	}

}
