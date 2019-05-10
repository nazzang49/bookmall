package bookmall.test;

import java.util.List;
import java.util.Scanner;

import bookmall.dao.MemberDAO;
import bookmall.main.Encryption;
import bookmall.vo.MemberVO;

public class MemberDAOTest {

	public static void main(String[] args) {
		//비밀번호 암호화
		String pw1 = new Encryption().encryptSHA256("abcdef");
		String pw2 = new Encryption().encryptSHA256("asqwuioefb");
		insertMemberTest("윤민호",pw1,"010-5678-8888","ymh@naver.com");
		insertMemberTest("박진영",pw2,"010-1234-5678","jyp@naver.com");
		getListMemberTest();
		//회원 정보 수정
		//updateMember();
	}
	
	public static void insertMemberTest(String name, String pw, String cell, String email) {
		MemberVO vo = new MemberVO();
		String encryptedPw = pw.substring(0, 15);
		vo.setName(name);
		vo.setPw(encryptedPw);
		vo.setCell(cell);
		vo.setEmail(email);
		if(new MemberDAO().insertMember(vo)) System.out.println("[추가완료]");
		else System.out.println("[추가실패]");
	}
	
	public static void getListMemberTest() {
		MemberVO vo = new MemberVO();
		List<MemberVO> list = new MemberDAO().getListMember();
		for(MemberVO m : list) {
			System.out.print("이름-"+m.getName()+" \t");
			System.out.print("전화번호-"+m.getCell()+" \t");
			System.out.print("이메일-"+m.getEmail());
			System.out.println();
		}
	}
	
	public static void updateMember() {
		Scanner sc = new Scanner(System.in);
		String chName = sc.nextLine();
		String chPw = sc.nextLine();
		chPw = new Encryption().encryptSHA256(chPw).substring(0, 15);
		//회원 존재 및 비밀번호 일치 여부 확인
		if(new MemberDAO().chkMemberInfo(chName,chPw)) {
			System.out.println("새로운 전화번호 / 이메일 입력 --> ");
			String chCell = sc.nextLine();
			String chEmail = sc.nextLine();
			MemberVO vo = new MemberVO();
			//이름과 비밀번호는 그대로
			vo.setName(chName);
			vo.setPw(chPw);
			vo.setCell(chCell);
			vo.setEmail(chEmail);
			new MemberDAO().updateMember(vo);
		}
		sc.close();
	}
}
