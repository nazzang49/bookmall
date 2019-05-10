package bookmall.test;

import java.util.List;

import bookmall.dao.CategoryDAO;
import bookmall.vo.CategoryVO;

public class CategoryDAOTest {

	public static void main(String[] args) {
		insertCategoryTest("인문");
		insertCategoryTest("과학");
		insertCategoryTest("정치");
		insertCategoryTest("사회");
		getListCategoryTest();
	}
	
	public static void insertCategoryTest(String name) {
		CategoryVO vo = new CategoryVO();
		vo.setName(name);
		if(new CategoryDAO().insertCategory(vo)) System.out.println("[추가완료]");
		else System.out.println("[추가실패]");
	}
	
	public static void getListCategoryTest() {
		CategoryVO vo = new CategoryVO();
		List<CategoryVO> list = new CategoryDAO().getListCategory();
		for(CategoryVO c : list) {
			System.out.print("번호-"+c.getNo()+" \t");
			System.out.print("카테고리명-"+c.getName());
			System.out.println();
		}
	}
}
