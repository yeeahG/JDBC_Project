package probono.model.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	//두개의 Properties 객체 필요
	//DB 정보 & sql 문장만 보유한 객체
	
	static Properties dbInfo = new Properties();
	//static Properties dbCreate = new Properties();
	//static Properties prohoSql = new Properties();
	
	static {
		try {
			dbInfo.load(new FileReader("db.properties"));
			//dbCreate.load(new FileReader("db.properties"));
			Class.forName(dbInfo.getProperty("jdbc.driver"));
			
	} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(dbInfo.getProperty("jdbc.url"), dbInfo.getProperty("jdbc.id"), dbInfo.getProperty("jdbc.pw"));
	}

	
	public static void close(Connection con, Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	public static void close(Connection con, Statement stmt, ResultSet rset) {
		try {
			if (rset != null) {
				rset.close();
				rset = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}
	
	
}
