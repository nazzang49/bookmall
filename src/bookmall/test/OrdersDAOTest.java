package bookmall.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import bookmall.dao.OrdersDAO;
import bookmall.vo.OrdersBookVO;
import bookmall.vo.OrdersVO;

public class OrdersDAOTest {

	public static void main(String[] args) {
		//현재 날짜 형식 포맷
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(now);
		
		//주문번호 호출
		long idx=new OrdersDAO().getOrderListNum();
		String order_code = date+"-"+idx;
		
		List<OrdersBookVO> list = new ArrayList<OrdersBookVO>();
		OrdersBookVO ob = new OrdersBookVO();
		ob.setBook_no(1);
		ob.setAmount(10);
		list.add(ob);
		
		OrdersBookVO ob1 = new OrdersBookVO();
		ob1.setBook_no(2);
		ob1.setAmount(30);
		list.add(ob1);
		
		//주문 등록
		//insertOrdersTest(0,1,order_code,"부산시 연제구 연산9동",1,list);
		//getListOrdersTest();
		getListOrderBookTest();
	}
	
	public static void insertOrdersTest(long price, long member_no, String order_code, String addr, long status, List<OrdersBookVO> list) {
		OrdersVO vo = new OrdersVO();
		vo.setPrice(price);
		vo.setMember_no(member_no);
		vo.setOrder_no(order_code);
		vo.setAddr(addr);
		
		long order_no = new OrdersDAO().insertOrder(vo, status);
		
		for(OrdersBookVO ob : list) {
			ob.setOrders_no(order_no);
			new OrdersDAO().insertOrderBook(ob);
		}
		
		long total = new OrdersDAO().getPrice(order_no);
		new OrdersDAO().updatePrice(order_no,total);
	}
	
	//주문 목록
	public static void getListOrdersTest() {
		OrdersVO vo = new OrdersVO();
		List<OrdersVO> list = new OrdersDAO().getListOrder();
		for(OrdersVO c : list) {
			System.out.print("주문번호-"+c.getOrder_no()+" \t");
			System.out.print("주문자-"+c.getMember_name()+" \t");
			System.out.print("주문자 이메일-"+c.getMember_email()+" \t");
			System.out.print("최종 결제금액-"+c.getPrice()+" \t");
			System.out.print("배송지-"+c.getAddr()+" \t");
			System.out.print("주문상태-"+c.getStatus());
			System.out.println();
		}
	}
	
	//주문 도서 목록
	public static void getListOrderBookTest() {
		OrdersVO vo = new OrdersVO();
		List<OrdersBookVO> list = new OrdersDAO().getListOrderBook();
		for(OrdersBookVO c : list) {
			System.out.print("도서번호-"+c.getBook_no()+" \t");
			System.out.print("도서제목-"+c.getTitle()+" \t");
			System.out.print("수량-"+c.getAmount());
			System.out.println();
		}
	}
}
