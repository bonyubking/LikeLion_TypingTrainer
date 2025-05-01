package com.typing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.typing.util.DBUtil.*; 

//DB 연결되는지 콘솔에서 확인할 수 있는 클래스 
public class DBTest {

	public static void main(String[] args) {
		
		String sql = "select * from sentences";
		
		try(
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		){
			while(rs.next()) {
				System.out.println(rs.getString("content")+"\t"+rs.getString("difficulty")+"\t"+rs.getString("language"));
				 }
			
		}catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

}
