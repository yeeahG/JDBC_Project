/* CREATE TABLE activist (
       activist_id          VARCHAR2(20)  PRIMARY KEY,
       name                 VARCHAR2(20) NULL,
       password             VARCHAR2(20) NULL,
       major                VARCHAR2(50) NULL
);  */
package probono.model;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import probono.model.dto.ActivistDTO;
import probono.model.util.DBUtil;


//기부자 table과 관계된 DAO 클래스 
public class ActivistDAO {
	
	static Properties sql = new Properties();
	static {
		try {
			sql.load(new FileReader("probonosql.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
		//기부자 등록(insert)
		public static boolean addActivist(ActivistDTO activist) throws SQLException{
			Connection con = null;
			PreparedStatement pstmt = null;
			try{
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(sql.getProperty("addActivist"));
				//pstmt = con.prepareStatement("insert into activist values(?, ?, ?, ?)");
				pstmt.setString(1, activist.getId());
				pstmt.setString(2, activist.getName());
				pstmt.setString(3, activist.getPassword());
				pstmt.setString(4, activist.getMajor());
				
				int result = pstmt.executeUpdate();
			
				if(result == 1){
					return true;
				}
			}finally{
				DBUtil.close(con, pstmt);
			}
			return false;
		}
	
		
		//수정  
		//기부자 id로 주요 기부 내용 수정하기
		public static boolean updateActivist(String activistId, String major) throws SQLException{
			Connection con = null;
			PreparedStatement pstmt = null;
			try{
				con = DBUtil.getConnection();
				
				pstmt = con.prepareStatement(sql.getProperty("updateActivist"));
				//pstmt = con.prepareStatement("update activist set major=? where activist_id=?");
				pstmt.setString(1, major);
				pstmt.setString(2, activistId);
				
				int result = pstmt.executeUpdate();
				if(result == 1){
					return true;
				}		
			}finally{
				DBUtil.close(con, pstmt);
			}
			return false;
		}

	
		//??? 삭제
		//sql - delete from activist where activist_id=?
		public static boolean deleteActivist(String activistId) throws SQLException{
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = DBUtil.getConnection();
				
				pstmt = con.prepareStatement(sql.getProperty("deleteActivist"));
				//pstmt = con.prepareStatement("delete from activist where activist_id=?)");
				pstmt.setString(1, activistId);
				
			} finally {
				DBUtil.close(con, pstmt);
			}
			return false;
		}
	
		//id로 해당 기부자의 모든 정보 반환
		public static ActivistDTO getActivist(String activistId) throws SQLException{
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ActivistDTO activist = null;
			
			try{
				con = DBUtil.getConnection();
				
				pstmt = con.prepareStatement(sql.getProperty("getActivist"));
				//pstmt = con.prepareStatement("select * from activist where activist_id=?");
				pstmt.setString(1, activistId);
				rset = pstmt.executeQuery();
				if(rset.next()){
					activist = new ActivistDTO(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4));
				}			
			}finally{
				DBUtil.close(con, pstmt, rset);
			}
			return activist;
		}

		//???모든 기부자 검색해서 반환
		//sql - select * from activist
		public static ArrayList<ActivistDTO> getAllActivists() throws SQLException{
			Connection con = null;
			Statement stmt = null;
			ResultSet rset = null;
			ArrayList<ActivistDTO> activist_list = null;
			
			try{
				con = DBUtil.getConnection();
				stmt = con.createStatement();
				rset = stmt.executeQuery(sql.getProperty("getAllActivists"));
				//rset = stmt.executeQuery("select * from activist");
				activist_list = new ArrayList<ActivistDTO>();
				while(rset.next()){
					activist_list.add(new ActivistDTO(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4)));
				}			
			}finally{
				DBUtil.close(con, stmt, rset);
			}
			return activist_list;
		}

}
