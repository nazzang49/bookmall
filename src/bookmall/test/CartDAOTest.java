package bookmall.test;

import java.util.List;
import java.util.Scanner;
import bookmall.dao.BookDAO;
import bookmall.dao.CartDAO;
import bookmall.vo.BookVO;
import bookmall.vo.CartVO;

public class CartDAOTest {

	public static void main(String[] args) {
		//책 검색
		//Scanner sc = new Scanner(System.in);
		//System.out.println("카테고리(1) or 직접검색(2) 번호 입력 --> ");
		//int num = sc.nextInt();
		
		//List<BookVO> list = new CartDAO().getListBy(num);
		//for(BookVO b : list) {
		//	System.out.print("제목-"+b.getTitle()+" \t");
		//	System.out.print("가격-"+b.getPrice());
		//	if(num!=2) System.out.print(" \t"+"카테고리명-"+b.getCategory_name());
		//	System.out.println();
		//}
		insertCartTest(1,1,15);
		insertCartTest(2,1,10);
		//getListCartTest(1);
	}
	
	public static void insertCartTest(long book_no, long member_no, long amount) {
		CartVO vo = new CartVO();
		vo.setBook_no(book_no);
		vo.setMember_no(member_no);
		vo.setAmount(amount);
		if(new CartDAO().insertCart(vo)) System.out.println("[추가완료]");
		else System.out.println("[추가실패]");
	}
	
	public static void getListCartTest(long member_no) {
		CartVO vo = new CartVO();
		List<CartVO> list = new CartDAO().getListCart(member_no);
		for(CartVO c : list) {
			System.out.print("제목-"+c.getBook_title()+" \t");
			System.out.print("수량-"+c.getAmount()+" \t");
			System.out.print("합계금액-"+c.getPrice());
			System.out.println();
		}
	}
}
