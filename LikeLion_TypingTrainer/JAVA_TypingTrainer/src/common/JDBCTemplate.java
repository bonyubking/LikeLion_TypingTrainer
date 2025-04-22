package common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	
	// case 1 : DB 실제값대입
	/*
	public static Connection getConnection() {
	Connection conn = null;
	try {
		
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_emp?" + "user=root&password=kus951024*");
	    
	    conn.setAutoCommit(false);
	    
	    if (!conn.isClosed()) {
	    	System.out.println("연결 중이야");
	    }
	    
	}	catch(Exception e) {}

	return conn;
	}
	*/
	
	/// Case 2. properties 파일활용
	public static Connection getConnection() {
		
		Connection conn = null;
		Properties prop = new Properties();
		
		try (InputStream input = JDBCTemplate.class.getClassLoader().getResourceAsStream("db.properties")){
			
			if (input == null) {
				throw new RuntimeException("db.properties 없음");
			}
			
			prop.load(input);
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			
		    Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		    
		    conn.setAutoCommit(false);
		    
		    if (!conn.isClosed()) {
		    	System.out.println("연결 중이야");
		    }
		    
		}	catch(Exception e) { 
		    e.printStackTrace();}
	
		return conn;
	}
	
	public static void Close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			}	catch (SQLException e) {
				System.out.println("데이터 연결 닫기 실패 : " + e.getSQLState()+ "\t" + e.getMessage());
			}
			
		}
		
	}

	
	public static void Close(Connection con) {
		if(con != null) {
			try {
				con.close();
			}	catch (SQLException e) {
				System.out.println("데이터 연결 닫기 실패 : " + e.getSQLState()+ "\t" + e.getMessage());
			}
			
		}
		
	}
	
	public static void Close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}	catch (SQLException e) {
				System.out.println("데이터 연결 닫기 실패 : " + e.getSQLState()+ "\t" + e.getMessage());
			}
			
		}
		
	}
	
// 4 -1 트랜잭션 commit
	public static void commit(Connection con) {
		if(con != null) {
			try {
				con.commit();
			}	catch (SQLException e) {
				System.out.println("데이터 연결 닫기 실패 : " + e.getSQLState()+ "\t" + e.getMessage());
			}
			
		}
		
	}
	
// 4-2 트랜잭션 rollback
	
	public static void rollback(Connection con) {
		if(con != null) {
			try {
				con.rollback();
			}	catch (SQLException e) {
				System.out.println("데이터 연결 닫기 실패 : " + e.getSQLState()+ "\t" + e.getMessage());
			}
			
		}
		
	}

}
