package kr.ac.sungkyul.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.ac.sungkyul.emaillist.vo.EmailListVo;

@Repository
public class EmailListDao {

	public List<EmailListVo> getList() {
		List<EmailListVo> list = new ArrayList<EmailListVo>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			
			connection = getConnection();

			stmt = connection.createStatement();

			String sql = "select no, last_name, first_name, email, to_char(reg_date, 'yyyy-dd-mm') from emaillist order by reg_date desc";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				String email = rs.getString(4);
				String regDate = rs.getString(5);

				EmailListVo vo = new EmailListVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				vo.setRegDate(regDate);

				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error" + e);
		}  finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("error" + e);
			}

		}

		return list;
	}
	
	
	
	public boolean insert(EmailListVo vo){
		

		PreparedStatement pstmt = null;
		int count = 0;
		Connection connection =null;
		try {
			connection = getConnection();
			
			getConnection();

			String sql = "insert into emaillist values(seq_emaillist.nextval, ?, ?, ?, sysdate)";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1,vo.getLastName());
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getEmail());
			
			count = pstmt.executeUpdate();

			

		} catch (SQLException e) {
			System.out.println("error" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("error" + e);
			}

		}

		return (count==1);
		
		
		
		
	}

	private Connection getConnection()throws SQLException {
		Connection connection = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
}
