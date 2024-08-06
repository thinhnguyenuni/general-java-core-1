package fa.training.utils;

public class BirthDayException extends Exception{
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	
	public BirthDayException(){
		System.err.println("Nhập năm sinh phải lớn hơn 1900, vui lòng nhập lại : ");
	}
	public String toString(){ 
	    return "Nhập năm sinh phải lớn hơn 1900, vui lòng nhập lại : ";
	}
}
