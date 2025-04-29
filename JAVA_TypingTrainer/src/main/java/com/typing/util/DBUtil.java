package com.typing.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {

	public static Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")){
			if(input == null) {
				throw new RuntimeException("db 파일 찾을 수 없음");
			}

			prop.load(input);
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");

			Class.forName(driver);
			
			conn = DriverManager.getConnection(url,user,password);
			conn.setAutoCommit(false);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return conn;
	} 

	public static void Close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				System.out.println("데이터베이스 연결 닫기 오류 : "+e.getMessage());
			}
		}
		
	}
	
	public static void Close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			}catch(SQLException e) {
				System.out.println("명령 오류 : "+e.getMessage());
			}
		}
		
	}
	
	public static void Close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(SQLException e) {
					System.out.println("쿼리 리턴 오류 : "+e.getMessage());
			}
		}
	}
	
	public static void commit(Connection conn) {
		if(conn != null) {
			try {
				conn.commit();
			}catch(SQLException e) {
					System.out.println("쿼리 리턴 오류 : "+e.getMessage());
			}
		}
	}
	
	public static void rollback(Connection conn) {
		if(conn != null) {
			try {
				conn.rollback();
			}catch(SQLException e) {
					System.out.println("쿼리 리턴 오류 : "+e.getMessage());
			}
		}
	}
}