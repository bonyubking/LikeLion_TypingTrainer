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
	public String findByUid(String uid) {
		Connection connection = DBUtil.sharedConnection();
		ResultSet rs = null;
		String result = null;
		try(
			PreparedStatement ps = connection.prepareStatement(selectUidSql)){
			ps.setString(1, uid);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {result = uid;}
			rs.close();
			connection.close();
			return result;
		}catch(SQLException e) {
			throw new RuntimeException("회원 DB 조회 중 오류 발생"+e);
		}
	}

	@Override
	public boolean checkNickname(String nickname) {
		// TODO Auto-generated method stub
		return false;
	}

}
