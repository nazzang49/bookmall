package bookmall.vo;

public class OrdersVO {

	private long no;
	private long member_no;
	//도서번호
	private long book_no;
	//도서제목
	private String book_title;
	//수량
	private long book_amount;
	private String member_name;
	private String member_email;
	//최종 결제 금액
	private long price;
	//주문번호 -> 데이트 포맷  필요
	private String order_no;
	private String addr;
	private String status;
	
	public String getBook_title() {
		return book_title;
	}
	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}
	
	public long getBook_amount() {
		return book_amount;
	}
	public void setBook_amount(long book_amount) {
		this.book_amount = book_amount;
	}
	public long getBook_no() {
		return book_no;
	}
	public void setBook_no(long book_no) {
		this.book_no = book_no;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public long getPrice() {
		return price;
	}
	public long getMember_no() {
		return member_no;
	}
	public void setMember_no(long member_no) {
		this.member_no = member_no;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return super.toString();
	}
}