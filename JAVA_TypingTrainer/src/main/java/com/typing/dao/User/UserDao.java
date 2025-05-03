package com.typing.dao.User;

import com.typing.model.dto.UserDto;
import com.typing.model.entity.User;

public interface UserDao {
	String insertSql = "INSERT INTO USERS(UID, PASSWORD, NICKNAME) VALUES(?,?,?)";
	String selectUidSql = "SELECT USER_ID, UID, PASSWORD, NICKNAME FROM USERS WHERE UID = ?";
	String selectNicknameSql = "SELECT UID, PASSWORD, NICKNAME FROM USERS WHERE NICKNAME = ?";
	
	void save(User user); // 회원 저장 
	UserDto findByUid(String uid); // 회원 ID 이용한 회원 조회
	UserDto checkNickname(String nickname); // 회원 닉네임 이용한 회원 조회
}
