package com.typing.dao.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.typing.model.dto.UserDto;
import com.typing.model.entity.User;
import com.typing.util.DBUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public void save(User user){
		Connection connection = DBUtil.sharedConnection();
		try(PreparedStatement ps = connection.prepareStatement(insertSql)){
			ps.setString(1, user.getuId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getNickname());
			ps.executeUpdate();
			connection.commit();
			ps.close();		
			connection.close();
		}catch(SQLException e){
			 throw new RuntimeException("회원 DB 저장 중 오류 발생"+e);
		}
	}

	@Override
	public UserDto findByUid(String uid) {
		Connection connection = DBUtil.sharedConnection();
		ResultSet rs = null;
		UserDto user = null;
		try(
			PreparedStatement ps = connection.prepareStatement(selectUidSql)){
			ps.setString(1, uid);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				String uId = rs.getString("uid");
				String password = rs.getString("password");
				String nickname = rs.getString("nickname");
				
				user = new UserDto(uId,password,nickname);
			}
			rs.close();
			connection.close();
			return user;
		}catch(SQLException e) {
			throw new RuntimeException("회원ID 이용한 DB 조회 중 오류 발생"+e);
		}
	}

	@Override
	public UserDto checkNickname(String nickname) {
		Connection connection = DBUtil.sharedConnection();
		ResultSet rs = null;
		boolean result = true;
		UserDto user = null;
		try(
			PreparedStatement ps = connection.prepareStatement(selectNicknameSql)){
			ps.setString(1, nickname);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String uId = rs.getString("uid");
				String password = rs.getString("password");
				
				user = new UserDto(uId,password,nickname);
			}
			rs.close();
			connection.close();
			return user;
		}catch(SQLException e) {
			throw new RuntimeException("회원 닉네임 이용한 DB 조회 중 오류 발생"+e);
		}
	}

}
