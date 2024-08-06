/**
 * 
 */
package fa.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import fa.training.entities.BangCap;
import fa.training.entities.Candidate;
import fa.training.entities.Experience;
import fa.training.entities.Fresher;
import fa.training.entities.Intern;
import fa.training.utils.ConnectionUtil;
import fa.training.utils.Validator;

/**
 * @author Van Thinh
 *
 */
public class CandidateDAO {

	/**
	 * 
	 */
	public static Scanner sc = new Scanner(System.in);

	/**
	 * Map data info of Student and ResultSet
	 * 
	 * @author: thinhnv30
	 * @birthday: 1989/11/24
	 * @param rs
	 * @return Student
	 * @throws SQLException
	 */
	public static Candidate mapRows(ResultSet resultSet) throws SQLException {

		// Tạo đối tượng Candidate từ dữ liệu ResultSet
		Candidate can = null;
		String candidateID = resultSet.getString("CandidateID");
		String fullName = resultSet.getString("fullName");

		Date birthDay = resultSet.getDate("BirthDay");

		String phone = resultSet.getString("Phone");
		String email = resultSet.getString("email");
		int type = resultSet.getInt("candidate_type");
		String proSkill = resultSet.getString("ProSkill");
		int expInYear = resultSet.getInt("ExpInYear");

		Date graduation_date = resultSet.getDate("graduation_date");

		String graduation_rank = resultSet.getString("Graduation_rank");
		String education = resultSet.getString("Education");
		String majors = resultSet.getString("Majors");
		String semester = resultSet.getString("Semester");
		String university_name = resultSet.getString("University_name");

		ArrayList<BangCap> listBangCap = BangCapDAO.selectBangCap(candidateID);

		if (type == 0) {
			can = new Experience(candidateID, fullName, birthDay, email, phone, type, listBangCap, proSkill, expInYear);
		} else if (type == 1) {
			can = new Fresher(candidateID, fullName, birthDay, email, phone, type, listBangCap, graduation_date,
					graduation_rank, education);
		} else if (type == 2) {
			can = new Intern(candidateID, fullName, birthDay, fullName, phone, type, listBangCap, majors, semester,
					university_name);
		}
		return can;
	}

	/**
	 * THINHNV30 1993-03-03 Lấy thông tin từ database
	 * 
	 * @throws ParseException
	 */
	public static void getCandidatesFromDatabase() {

		Connection conn = ConnectionUtil.getConnection();
		ArrayList<Candidate> candidates = new ArrayList<>();
		String query = "SELECT * FROM Candidate";
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			if (!resultSet.next()) {
				System.out.println("No data to display!!!");
				return;
			}
			
			do {
				// Tạo đối tượng Candidate từ dữ liệu ResultSet
				Candidate Candidate = mapRows(resultSet);
				candidates.add(Candidate);
			} while (resultSet.next());
			
			Collections.sort(candidates);
			showInformation((ArrayList<Candidate>) candidates);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * THINHNV30 1993-03-03 Lấy thông tin từ database
	 * 
	 * @throws ParseException
	 */
	public static void getCandidatesLikeID(String candidateID) {
		
		Connection conn = ConnectionUtil.getConnection();
		ArrayList<Candidate> candidates = new ArrayList<>();
		String query = "SELECT * FROM Candidate WHERE candidateID = ?";
		try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
				preparedStatement.setString(1, candidateID);
				ResultSet resultSet = preparedStatement.executeQuery();
			
//			if (!resultSet.next()) {
//				System.out.println("No data to display!!!");
//				return;
//			}
			
			while (resultSet.next()) {
				// Tạo đối tượng Candidate từ dữ liệu ResultSet
				Candidate Candidate = mapRows(resultSet);
				candidates.add(Candidate);
			}
			
			Collections.sort(candidates);
			showInformation((ArrayList<Candidate>) candidates);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * THINHNV30 1993-03-03 GIỚI THIỆU THÔNG TIN CỦA CÁC HÀNH KHÁCH HIỆN CÓ
	 */
	public static void showInformation(ArrayList<Candidate> candidates) {
		for (Candidate candidate : candidates) {
			System.out.println(candidate.showInfo());
			System.out.println(); // In một dòng trống để phân tách giữa các hành khách
		}
	}

	/**
	 * THINHNV30 1993-03-03 GIỚI THIỆU THÔNG TIN CỦA CÁC HÀNH KHÁCH HIỆN CÓ
	 * 
	 * @throws ParseException
	 */
//	public static void getInformation() {
//
//		Connection conn = ConnectionUtil.getConnection();
//		// Tạo một danh sách hành khách
//		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
//		candidates = getCandidatesFromDatabase(conn);
//
//		// Gọi phương thức showInformation để hiển thị thông tin của hành khách
//		showInformation(candidates);
//	}

	/**
	 * phương thức insert bằng result set
	 * 
	 * @param c
	 * @return
	 */
	public static int insertCDR_M(Candidate c) {

		Connection con = ConnectionUtil.getConnection();
		String sql = "select * from Candidate";
		try (PreparedStatement pstm = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE)) {

			ResultSet rs = pstm.executeQuery();

			Date BirthDay = new Date();
			BirthDay = c.getBirthDay();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = dateFormat.format(BirthDay);

			rs.moveToInsertRow();

			rs.updateString("CandidateID", c.getCandidateID());
			rs.updateString("FullName", c.getFullName());
			rs.updateString("BirthDay", formattedDate);
			rs.updateString("Phone", c.getPhone());
			rs.updateString("Email", c.getEmail());
			rs.updateInt("Candidate_type", c.getCandidate_type());

			if (c instanceof Experience) {
				rs.updateInt("ExpInYear", ((Experience) c).getExpInYear());
				rs.updateString("ProSkill", ((Experience) c).getProSkill());
			}

			if (c instanceof Fresher) {
				Date GraduationDate = new Date();
				GraduationDate = ((Fresher) c).getGraduation_date();
				SimpleDateFormat GraduationFormat = new SimpleDateFormat("yyyy-MM-dd");
				String FormGraduation = GraduationFormat.format(GraduationDate);

				rs.updateString("Graduation_rank", ((Fresher) c).getGraduation_rank());
				rs.updateString("Education", ((Fresher) c).getEducation());
				rs.updateString("Graduation_date", FormGraduation);
			}

			if (c instanceof Intern) {
				rs.updateString("Majors", ((Intern) c).getMajors());
				rs.updateString("Semester", ((Intern) c).getSemester());
				rs.updateString("University_name", ((Intern) c).getUniversity_name());
			}
//			con.setAutoCommit(false);
			rs.insertRow();
			BangCapDAO.insertMBC(con, c.bangCapList);
//			con.commit();

			return 1;

		} catch (SQLException e) {
			e.printStackTrace();
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}

			return 0;

		}
	}

	/**
	 * insert data bằng result set
	 */
	public static void insertCDR() {

		int result;
		Candidate c;
		int candidate_type = Validator.isValidType(sc);
		if (candidate_type == 0) {
			c = new Experience();
			c.setCandidate_type(candidate_type);
			c.input(true);
		} else if (candidate_type == 1) {
			c = new Fresher();
			c.setCandidate_type(candidate_type);
			c.input(true);
		} else {
			c = new Intern();
			c.setCandidate_type(candidate_type);
			c.input(true);
		}
		result = insertCDR_M(c);
		if (result > 0) {
			System.out.println("Insert ứng viên thành công!");
		} else
			System.out.println("Insert ứng viên thất bại!");

		Candidate.canidate_count++;
		System.out.println("Số cột được insert là : " + Candidate.canidate_count);
	}

	/**
	 * Insert Candidate
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static void updateResult(Candidate can) {

		Connection con = ConnectionUtil.getConnection();
		String sql = "SELECT * FROM Candidate where  candidateID=?";
		try (PreparedStatement prstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE)) {
			prstmt.setString(1, can.getCandidateID());
			ResultSet rs = prstmt.executeQuery();
			
			System.out.println("\n Trước khi update: ");
			printRs(rs,can);
			rs.beforeFirst();
			
			while (rs.next()) {
				rs.updateString("fullname", can.getFullName());

				Date certificatedDate = new Date();
				certificatedDate = can.getBirthDay();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = dateFormat.format(certificatedDate);
				rs.updateString("BirthDay", formattedDate);

				rs.updateString("Phone", can.getPhone());
				rs.updateString("Email", can.getEmail());
				rs.updateInt("Candidate_type", can.getCandidate_type());

				if (can instanceof Experience) {
//					Experience exp = (Experience) can;

					rs.updateString("ProSkill", ((Experience) can).getProSkill());
					rs.updateInt("ExpInYear", ((Experience) can).getExpInYear());
					rs.updateString("graduation_date", null);
					rs.updateString("graduation_rank", null);
					rs.updateString("education", null);
					rs.updateString("majors", null);
					rs.updateString("semester", null);
					rs.updateString("university_name", null);

				}
				if (can instanceof Fresher) {
					rs.updateString("ProSkill", null);
					rs.updateString("ExpInYear", null);
					Date Graduation_date = new Date();

					rs.updateString("Graduation_rank", ((Fresher) can).getGraduation_rank());
					rs.updateString("Education", ((Fresher) can).getEducation());
					Graduation_date = ((Fresher) can).getBirthDay();
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate1 = dateFormat1.format(Graduation_date);
					rs.updateString("Graduation_date", formattedDate1);

					rs.updateString("majors", null);
					rs.updateString("semester", null);
					rs.updateString("university_name", null);
				}
				if (can instanceof Intern) {
					rs.updateString("ProSkill", null);
					rs.updateString("ExpInYear", null);
					rs.updateString("graduation_date", null);
					rs.updateString("graduation_rank", null);
					rs.updateString("education", null);

					rs.updateString("Majors", ((Intern) can).getMajors());
					rs.updateString("Semester", ((Intern) can).getSemester());
					rs.updateString("University_name", ((Intern) can).getUniversity_name());
				}
//				con.setAutoCommit(false);
				rs.updateRow();
				System.out.println("\n Sau khi update: ");
				printRs(rs, can);
				
				ArrayList<BangCap> bangCapList = can.getBangCapList();
				for (BangCap bangCap : bangCapList) {
					BangCapDAO.updateBCResult_M(con, bangCap, can.getCandidateID(), bangCap.getCertificatedID());
				}
//				con.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Update Candidate that bai!!!");
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
		}
	}

	/**
	 * insert data bằng result set
	 */
	public static void updateCandidate() {
		Candidate c;

		int candidate_type = Validator.isValidType(sc);
		if (candidate_type == 0) {
			c = new Experience();
			c.setCandidate_type(candidate_type);
			c.input(false);
			System.out.println("****");
		} else if (candidate_type == 1) {
			c = new Fresher();
			c.setCandidate_type(candidate_type);
			c.input(false);
			System.out.println("****");
		} else {
			c = new Intern();
			c.setCandidate_type(candidate_type);
			c.input(false);
			System.out.println("****");
		}
		updateResult(c);
		Candidate.canidate_count++;
		System.out.println("Số cột được insert là : " + Candidate.canidate_count);
	}

	/**
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static void printRs(ResultSet rs,Candidate can)  {

		// Bao dam rang chung ta bat dau tu hang dau tien
		try {
			rs.beforeFirst();
			while (rs.next()) {
				// Lay du lieu boi su dung ten cot
				String candidateID = rs.getString("candidateID");
				String fullName = rs.getString("fullName");
				Date Birthday = rs.getDate("Birthday");
				String phone = rs.getString("phone");
				int candidate_type = rs.getInt("candidate_type");
				
				// Hien thi cac gia tri
				System.out.print("\ncandidateID: " + candidateID);
				System.out.print("\nfullName: " + fullName);
				System.out.print("\nBirthday: " + Birthday);
				System.out.print("\nphone: " + phone);
				System.out.print("\ncandidate_type: " + candidate_type);
				
				if (can instanceof Experience) {
					String proSkill = rs.getString("proSkill");
					String expInYear = rs.getString("expInYear");
					System.out.print("\nGraduation_rank: " + proSkill);
					System.out.print("\nGraduation_date: " + expInYear);
				}
				if (can instanceof Fresher) {
					String Education = rs.getString("Education");
					String Graduation_rank = rs.getString("Graduation_rank");
					Date Graduation_date = rs.getDate("Graduation_date");
					System.out.print("\nEducation: " + Education);
					System.out.print("\nGraduation_rank: " + Graduation_rank);
					System.out.print("\nGraduation_date: " + Graduation_date);
				}
				if (can instanceof Intern) {
					String Majors = rs.getString("Majors");
					String Semester = rs.getString("Semester");
					String University_name = rs.getString("University_name");
					System.out.print("\nMajors: " + Majors);
					System.out.print("\nSemester: " + Semester);
					System.out.print("\nUniversity_name: " + University_name);
				}
				
				System.out.print("\n=================");
			}
			System.out.println();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// Ket thuc printRs()
	
	/**
	 * Update carNumber of Candidate table, using ResultSet
	 * 
	 * @author: thinhnv30
	 */
	public static void updateOneRow() {

		Candidate candidate = null;
		System.out.println("Nhập CandidateID cần update: ");
		String CandidateID = sc.nextLine();

		String sql = "SELECT * FROM Candidate WHERE CandidateID=?";
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement prstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prstmt.setString(1, CandidateID);
			ResultSet rs = prstmt.executeQuery();
			if (!rs.next()) {
				System.out.println("No found Candidate");
				return;
			}
			System.out.println("Data before update");
			candidate = mapRows(rs);
			System.out.println(candidate.showInfo());
			String phone;
			do {
				System.out.println("Nhập số phone mới: ");
				phone = sc.nextLine().trim().toUpperCase();

				break;
			} while (true);
			rs.absolute(1);
			rs.updateString("phone", phone);
			rs.updateRow();

			System.out.println("\nData after update:");
			rs.first();
			System.out.println(candidate.showInfo());
			rs.close();
			prstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		
//		System.out.println("nhập candidateID cần tìm: ");
//		String c =sc.nextLine();
//		getCandidatesLikeID(c);
		updateCandidate();
//		insertCandidate();
//		updateOneRow();
//		getCandidatesFromDatabase();
		
	}
}
