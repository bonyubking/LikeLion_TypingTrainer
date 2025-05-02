package com.typing.dao.User;

import com.typing.model.entity.User;

public interface UserDao {
	String insertSql = "INSERT INTO USERS(UID, PASSWORD, NICKNAME) VALUES(?,?,?)";
	String selectUidSql = "SELECT UID FROM USERS WHERE UID = ?";
	String selectNicknameSql = "SELECT NICKNAME FROM USERS WHERE NICKNAME = ?";
	
	void save(User user); // 회원 저장 
	String findByUid(String uid); // 회원 ID 이용한 회원 조회
	boolean checkNickname(String nickname); // 회원 닉네임 이용한 회원 조회
}
