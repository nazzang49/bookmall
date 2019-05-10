package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrdersBookVO;
import bookmall.vo.OrdersVO;

public class OrdersDAO {

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
	
	public List<OrdersVO> getListOrder() {
		List<OrdersVO> list = new ArrayList<>();
		try {
			conn = getConnection();

			sql = "select o.order_no, m.name, m.email, o.price, o.addr, o.status" + 
					" from orders o, member m" + 
					" where o.member_no=m.no";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrdersVO vo = new OrdersVO();
				vo.setOrder_no(rs.getString(1));
				vo.setMember_name(rs.getString(2));
				vo.setMember_email(rs.getString(3));
				vo.setPrice(rs.getLong(4));
				vo.setAddr(rs.getString(5));
				vo.setStatus(rs.getString(6));
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
	
	//for 주문번호 생성
	public long getOrderListNum() {
		//현재 주문 대기 중인 리스트 갯수
		long flag = 0;
		try {
			conn = getConnection();

			sql = "select count(*) from orders";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				flag = rs.getLong(1);
			}
		} catch (SQLException e) {
			System.out.println("[에러 발생]");
			e.printStackTrace();
		} finally {
			close();
		}
		return flag;
	}
	
	public long insertOrder(OrdersVO vo, long status) {
		long order_no = 0;
		try {
			conn = getConnection();

			String str = "";
			if(status==0) str="배송 대기 중";
			else if(status==1) str="상차 완료";
			else if(status==2) str="배달원 수령 완료";
			else if(status==3) str="하차 완료";
			else if(status==4) str="최종 수령 장소 이동 중";
			else str="배송 완료";
			
			sql = "insert into orders values(null,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1,vo.getOrder_no());
			pstmt.setLong(2,vo.getPrice());
			pstmt.setString(3,vo.getAddr());
			pstmt.setString(4,str);
			pstmt.setLong(5,vo.getMember_no());
			pstmt.executeUpdate();
			
			//주문 추가 후 번호 반환
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) order_no = rs.getLong(1);
			
		} catch (SQLException e) {
			System.out.println("[에러 발생]");
			e.printStackTrace();
		} finally {
			close();
		}
		return order_no;
	}
	
	public boolean insertOrderBook(OrdersBookVO vo) {
		int flag = 0;
		try {
			conn = getConnection();
			
			sql = "insert into order_book values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,vo.getOrders_no());
			pstmt.setLong(2,vo.getBook_no());
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
	
	public List<OrdersBookVO> getListOrderBook() {
		List<OrdersBookVO> list = new ArrayList<>();
		try {
			conn = getConnection();

			sql = "select a.book_no, c.title, a.book_amount, b.order_no" + 
					" from order_book a, orders b, book c" + 
					" where a.order_no = b.no" + 
					" and a.book_no = c.no";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrdersBookVO vo = new OrdersBookVO();
				vo.setBook_no(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setAmount(rs.getLong(3));
				vo.setOrders_code(rs.getString(4));
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
	
	//현재금액 추출
	public long getPrice (long ordersNo) {
		long result = 0;
		try {
			conn = getConnection();
			String sql = "select sum(b.price * a.book_amount)" + 
					" from order_book a, book b" + 
					" where a.book_no = b.no" + 
					" and a.order_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ordersNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getLong(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return result;
	}
	
	//결제금액 갱신
	public boolean updatePrice(long ordersNo, long money) {
		int flag = 0;
		try {
			conn = getConnection();
			
			String sql = "update orders set price=? where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, money);
			pstmt.setLong(2, ordersNo);
			
			flag = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		if(flag==1) return true;
		return false;
	}
}
