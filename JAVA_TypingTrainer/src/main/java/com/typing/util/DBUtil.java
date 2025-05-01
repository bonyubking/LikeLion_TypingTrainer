package com.typing.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
 * DB 설정 
 * ChatServer 외에 모든 CRUD는 sharedConnection 사용
 * */
public class DBUtil {

	// ThreadLocal로 각 스레드마다 독립적인 DB 연결을 관리
    private static ThreadLocal<Connection> threadLocalConnection = ThreadLocal.withInitial(() -> null);
  
   // 모든 스레드가 공유하는 DB 커넥션 
   public static Connection sharedConnection() {
	   Connection sharedConnection = null; 
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
			
			sharedConnection = DriverManager.getConnection(url,user,password);
			sharedConnection.setAutoCommit(false);
		}catch(Exception e){
			System.out.println("SharedConnection error : "+e.getMessage());
		}
		return sharedConnection;
   }
    
   // 각 스레드가 독립적으로 사용하는 DB 커넥션 
	public static Connection getConnection() {
		Connection conn = threadLocalConnection.get();
		
		if(conn == null) {
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
				
				threadLocalConnection.set(conn);
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		}
		return conn;
	} 

	public static void Close() {
		Connection conn = threadLocalConnection.get();
		if(conn != null) {
			try {
				conn.close();
				threadLocalConnection.remove(); 
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