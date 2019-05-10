package bookmall.main;

import java.util.List;

import bookmall.dao.BookDAO;
import bookmall.dao.CartDAO;
import bookmall.dao.CategoryDAO;
import bookmall.dao.MemberDAO;
import bookmall.dao.OrdersDAO;
import bookmall.test.MemberDAOTest;
import bookmall.vo.BookVO;
import bookmall.vo.CartVO;
import bookmall.vo.CategoryVO;
import bookmall.vo.MemberVO;
import bookmall.vo.OrdersBookVO;
import bookmall.vo.OrdersVO;

//bookmall main
public class BookMall {

	public static void main(String[] args) {
		System.out.println("[카테고리 목록]");
		displayCategoryList();
		System.out.println();
		System.out.println("[도서 목록]");
		displayBookList();
		System.out.println();
		System.out.println("[회원 목록]");
		displayMemberList();
		System.out.println();
		//[회원 정보 수정]
		//new MemberDAOTest().updateMember();
		System.out.println("[1번 회원 카트 목록]");
		displayCartList(1);
		System.out.println();
		System.out.println("[2번 회원 카트 목록]");
		displayCartList(2);
		System.out.println();
		System.out.println("[주문 목록]");
		displayOrderList();
		System.out.println();
		System.out.println("[주문 도서 목록]");
		displayOrderBookList();
	}
	
	public static void displayCategoryList() {
		CategoryVO vo = new CategoryVO();
		List<CategoryVO> list = new CategoryDAO().getListCategory();
		for(CategoryVO c : list) {
			System.out.print("번호-"+c.getNo()+" \t");
			System.out.print("카테고리명-"+c.getName());
			System.out.println();
		}
	}
	
	public static void displayBookList() {
		BookVO vo = new BookVO();
		List<BookVO> list = new BookDAO().getListBook();
		for(BookVO b : list) {
			System.out.print("제목-"+b.getTitle()+" \t");
			System.out.print("가격-"+b.getPrice());
			System.out.println();
		}
	}

	public static void displayMemberList() {
		MemberVO vo = new MemberVO();
		List<MemberVO> list = new MemberDAO().getListMember();
		for(MemberVO m : list) {
			System.out.print("이름-"+m.getName()+" \t");
			System.out.print("전화번호-"+m.getCell()+" \t");
			System.out.print("이메일-"+m.getEmail());
			System.out.println();
		}
	}
	
	public static void displayCartList(long member_no) {
		CartVO vo = new CartVO();
		List<CartVO> list = new CartDAO().getListCart(member_no);
		for(CartVO c : list) {
			System.out.print("제목-"+c.getBook_title()+" \t");
			System.out.print("수량-"+c.getAmount()+" \t");
			System.out.print("합계금액-"+c.getPrice());
			System.out.println();
		}
	}
	
	public static void displayOrderList() {
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
	
	public static void displayOrderBookList() {
		OrdersVO vo = new OrdersVO();
		List<OrdersBookVO> list = new OrdersDAO().getListOrderBook();
		for(OrdersBookVO c : list) {
			System.out.print("도서번호-"+c.getBook_no()+" \t");
			System.out.print("도서제목-"+c.getTitle()+" \t");
			System.out.print("수량-"+c.getAmount()+" \t");
			System.out.print("주문번호-"+c.getOrders_code());
			System.out.println();
		}
	}
}
