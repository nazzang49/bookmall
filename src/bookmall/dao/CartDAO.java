package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import bookmall.vo.BookVO;
import bookmall.vo.CartVO;

public class CartDAO {

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
	
	//카테고리 or 직접검색
	public List<BookVO> getListBy(int num) {
		List<BookVO> list = new ArrayList<>();
		try {
			conn = getConnection();

			if(num==1) {
				sql = "select b.title, b.price, c.name from book b, category c"
						+" where b.category_no=c.no order by c.no";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					BookVO vo = new BookVO();
					vo.setTitle(rs.getString(1));
					vo.setPrice(rs.getLong(2));
					vo.setCategory_name(rs.getString(3));
					list.add(vo);
				}
			}else {
				Scanner sc = new Scanner(System.in);
				String keyword = sc.nextLine();
				
				sql = "select title, price from book where title like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					BookVO vo = new BookVO();
					vo.setTitle(rs.getString(1));
					vo.setPrice(rs.getLong(2));
					list.add(vo);
				}
			}
		} catch (SQLException e) {
			System.out.println("[에러 발생]");
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	public boolean insertCart(CartVO vo) {
		int flag = 0;
		try {
			conn = getConnection();

			sql = "insert into cart values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,vo.getBook_no());
			pstmt.setLong(2,vo.getMember_no());
			pstmt.setLong(3,vo.getAmount());
			
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
	
	public List<CartVO> getListCart(long member_no) {
		List<CartVO> list = new ArrayList<>();
		try {
			conn = getConnection();

			//책 제목, 수량, 책별 합계금액
			sql = "select b.title, c.amount, b.price*c.amount" + 
					" from book b, cart c, member m" + 
					" where b.no=c.book_no" + 
					" and c.member_no=m.no and m.no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, member_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CartVO vo = new CartVO();
				vo.setBook_title(rs.getString(1));
				vo.setAmount(rs.getLong(2));
				vo.setPrice(rs.getLong(3));
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
}