package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bookmall.vo.MemberVO;

public class MemberDAO {

	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static String sql = "";
	
	public static void close() {
		try {
			if(rs!=null&&!rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}try {
			if(pstmt!=null&&!pstmt.isClosed()) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}try {
			if(conn!=null&&!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//DB 연결
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:mariadb://14.32.18.221:3307/bookmall";
		String user = "bookmall";
		String pw = "bookmall";
		try {
			conn = DriverManager.getConnection(url, user, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public List<MemberVO> getListMember(){
		List<MemberVO> list = new ArrayList<>();
		try {
			conn = getConnection();

			//조인 SQL
			sql = "select no, name, cell, email from member";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setCell(rs.getString(3));
				vo.setEmail(rs.getString(4));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("[에러 발생]");
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	public boolean insertMember(MemberVO vo) {
		int flag = 0;
		try {
			conn = getConnection();

			sql = "insert into member values(null,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getCell());
			pstmt.setString(4, vo.getEmail());
			
			flag = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[에러 발생]");
			e.printStackTrace();
		} finally {
			close();
		}
		if(flag==1) return true;
		return false;
	}
	
	public boolean chkMemberInfo(String name, String pw) {
		int flag = 0;
		try {
			conn = getConnection();

			sql = "select pw from member where name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			//해당하는 회원정보가 있으면
			if(rs.next()) {
				//비밀번호 일치 여부 확인
				if(rs.getString(1).equals(pw)) {
					flag = 1;
				}
			}
		} catch (SQLException e) {
			System.out.println("[에러 발생]");
			e.printStackTrace();
		} finally {
			close();
		}
		if(flag==1) return true;
		return false;
	}
	
	public boolean updateMember(MemberVO vo) {
		int flag = 0;
		try {
			conn = getConnection();

			sql = "update member set cell=?, email=? where name=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getCell());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPw());
			
			flag = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("[에러 발생]");
			e.printStackTrace();
		} finally {
			close();
		}
		if(flag==1) return true;
		return false;
	}
}
