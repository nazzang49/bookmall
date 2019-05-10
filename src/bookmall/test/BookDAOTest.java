package bookmall.test;

import java.util.List;

import bookmall.dao.BookDAO;
import bookmall.vo.BookVO;

public class BookDAOTest {

	public static void main(String[] args) {
		insertBookTest("정치학",30000,3);
		insertBookTest("사회학",25000,4);
		insertBookTest("물리학",20000,2);
		insertBookTest("인문학",10000,1);
		getListBookTest();
	}
	
	public static void insertBookTest(String title, long price, long category_no) {
		BookVO vo = new BookVO();
		vo.setTitle(title);
		vo.setPrice(price);
		vo.setCategory_no(category_no);
		if(new BookDAO().insertBook(vo)) System.out.println("[추가완료]");
		else System.out.println("[추가실패]");
	}
	
	public static void getListBookTest() {
		BookVO vo = new BookVO();
		List<BookVO> list = new BookDAO().getListBook();
		for(BookVO b : list) {
			System.out.print("제목-"+b.getTitle()+" \t");
			System.out.print("가격-"+b.getPrice());
			System.out.println();
		}
	}
}