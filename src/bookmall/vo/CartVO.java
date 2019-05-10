package bookmall.vo;

public class CartVO {

	private long book_no;
	private long member_no;
	private long amount;
	private String book_title;
	private long price;
	
	public String getBook_title() {
		return book_title;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public void setBook_title(String book_title) {
		this.book_title = book_title;
	}
	
	public long getBook_no() {
		return book_no;
	}
	public void setBook_no(long book_no) {
		this.book_no = book_no;
	}
	public long getMember_no() {
		return member_no;
	}
	public void setMember_no(long member_no) {
		this.member_no = member_no;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
