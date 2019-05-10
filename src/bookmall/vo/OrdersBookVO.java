package bookmall.vo;

public class OrdersBookVO {
	private long amount;
	//주문번호
	private long orders_no;
	//주문코드
	private String orders_code;
	private long book_no;
	private String title;
	
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public long getOrders_no() {
		return orders_no;
	}
	public void setOrders_no(long orders_no) {
		this.orders_no = orders_no;
	}
	public String getOrders_code() {
		return orders_code;
	}
	public void setOrders_code(String orders_code) {
		this.orders_code = orders_code;
	}
	public long getBook_no() {
		return book_no;
	}
	public void setBook_no(long book_no) {
		this.book_no = book_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}

