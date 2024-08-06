package fa.training.utils;

public class EmailException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailException() {
		
	}
	
	@Override
	public String toString() {
		return "Nhập email không đúng vui lòng nhập lại: ";
	}
}
