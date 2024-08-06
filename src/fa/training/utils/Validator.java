/**
 * 
 */
package fa.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


/**
 * @author Van Thinh
 *
 */
public class Validator {

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 * 
	 */
	public static String isValidID(boolean isInsertFlg, Scanner sc) {
		String candidateID1 = "";
		do {
			System.out.println("Nhập CandidateID (ví dụ CD0009) ");
			candidateID1 = sc.nextLine().strip().toUpperCase();

			while (candidateID1.matches("^CD\\d{4}$") == false) {
				System.out.println("Bạn nhập CandidateID không đúng yêu cầu, vui lòng nhập lại");
				candidateID1 = sc.nextLine().strip().toUpperCase();
			}
//			System.out.println(Methods.kiemTraTonTai(candidateID1));

			if (isInsertFlg && kiemTraTonTai(candidateID1) == true) {
				System.out.println("candidateID1 đã tồn tại, vui lòng nhập lại");
			}

		} while (candidateID1.matches("^CD\\d{4}$") == false || isInsertFlg && kiemTraTonTai(candidateID1) == true);// flag==false
		return candidateID1;
	}

	/**
	 * kiểm tra tồn tại candidateId
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static boolean kiemTraTonTai(String candidateID) {

		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con
						.prepareStatement("select [candidateID] from [dbo].[Candidate] where [candidateID]=?")) {

			prst.setString(1, candidateID);

			ResultSet rs = prst.executeQuery();
			
			if (rs.next())
				return true;
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 * 
	 */
	public static Date isValidDay(Scanner sc) {
		Date birthDay = null;
		String birthDayStr;
		boolean valid;
		do {
			valid = false;
			birthDayStr = sc.nextLine();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			try {
				birthDay = sdf.parse(birthDayStr);
				Calendar c = Calendar.getInstance();
				c.setTime(birthDay);// date của SQL

				int year = c.get(Calendar.YEAR);
				if (year < 1900)
					throw new BirthDayException();

				if (year >= 1900) {
					valid = true;
				}
			} catch (ParseException e) {
//				e.printStackTrace();
				System.out.println("Nhập không đúng định dạng, vui lòng nhập lại");
			} catch (BirthDayException bd) {
//				bd.toString();
//				System.err.println("Nhập năm sinh phải lớn hơn 1900, vui lòng nhập lại : ");
			}
		} while (valid == false);
		return birthDay;
	}

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static String isValidPhone(Scanner sc) {
		String phone;
		do {
			System.out.println("Nhập phone (ví dụ 0905012345) ");
			phone = sc.nextLine().strip();

			if (phone.matches("^\\d{10}$") == false) {
				System.out.println("Bạn nhập phone không đúng yêu cầu, vui lòng nhập lại");
			}
		} while (phone.matches("^\\d{10}$") == false);// flag==false
		return phone;
	}

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static String isValidEmail(Scanner sc) {
		String email;
		do {
			System.out.println("Nhập email (ví dụ an@gmail.com) ");
			email = sc.nextLine().strip();
			try {
				if (email.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$") == false) {
					throw new EmailException();
				}
			} catch (EmailException ex) {
//				ex.printStackTrace();
				System.out.println(ex.toString());
			}
		} while (email.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$") == false);// flag==false
		return email;
	}

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 * 
	 */
	public static int isValidType(Scanner sc) {
		int candidate_type;
		do {
			try {
				System.out
						.println("Nhập Candidate_type có giá trị tương ứng là 0: Experience, 1: Fresher , 2: Intern ");
				candidate_type = Integer.parseInt(sc.nextLine());

				if (candidate_type > 2 || candidate_type < 0) {
					System.err.println("Nhập Candidate_type không hợp lệ vui lòng nhập lại!");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Không hợp lệ vui lòng nhập lại!");
			}
		} while (true);
		return candidate_type;
	}

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 * 
	 */
	public static int isValid0_1(Scanner sc) {
		int candidate_type;
		do {
			try {
				System.out.println("Nhập  0 hoặc 1 : ");
				candidate_type = Integer.parseInt(sc.nextLine());

				if (candidate_type > 1 || candidate_type < 0) {
					System.err.println("Nhập  không hợp lệ vui lòng nhập lại!");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Không hợp lệ vui lòng nhập lại!");
			}
		} while (true);
		return candidate_type;
	}

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 * 
	 */
	public static String isValidCfID(Scanner sc) {
		String CertificatedID = "";
		do {

			CertificatedID = sc.nextLine().strip().toUpperCase();

			if (CertificatedID.matches("^CF\\d{4}$") == false) {
				System.out.println("Bạn nhập CandidateID không đúng yêu cầu, vui lòng nhập lại");

			}
		} while (CertificatedID.matches("^CF\\d{4}$") == false);// flag==false
		return CertificatedID;
	}

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 * 
	 */
	public static int isValidMore0(Scanner sc) {
		int expInYear;
		do {
			try {

				expInYear = Integer.parseInt(sc.nextLine());

				if (expInYear < 0) {
					System.err.println("Nhập phải lớn hơn 0!!!");

				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Không hợp lệ vui lòng nhập lại!");

			}
		} while (true);
		return expInYear;
	}

}
