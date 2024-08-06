package fa.training.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;

import fa.training.dao.BangCapDAO;
import fa.training.dao.ExperienceDAO;
import fa.training.dao.FresherDAO;
import fa.training.dao.InternDAO;
import fa.training.entities.Candidate;
import fa.training.entities.Experience;
import fa.training.entities.Fresher;
import fa.training.entities.Intern;
import fa.training.utils.ConnectionUtil;
import fa.training.utils.Validator;

public class Methods {

	/**
	 * 
	 */
	public static Scanner sc = new Scanner(System.in);

	/**
	 * Insert Candidate
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static Candidate insertCandidateGetCandidateID(Candidate can) {

		Candidate candidate = null;

		String sql = "INSERT INTO CANDIDATE (CandidateID,fullName, birthDay, phone, email, candidate_type, expInYear, proSkill, graduation_date, graduation_rank, education, majors, semester, university_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement prstmt = conn.prepareStatement(sql)) {

			prstmt.setString(1, can.getCandidateID());
			prstmt.setString(2, can.getFullName());
			prstmt.setDate(3, new java.sql.Date(can.getBirthDay().getTime()));
			prstmt.setString(4, can.getPhone());
			prstmt.setString(5, can.getEmail());
			prstmt.setInt(6, can.getCandidate_type());
			if (can instanceof Experience) {
				prstmt.setInt(7, ((Experience) can).getExpInYear());
				prstmt.setString(8, ((Experience) can).getProSkill());
				prstmt.setString(9, null);
				prstmt.setString(10, null);
				prstmt.setString(11, null);
				prstmt.setString(12, null);
				prstmt.setString(13, null);
				prstmt.setString(14, null);
			}
			if (can instanceof Fresher) {
				prstmt.setString(7, null);
				prstmt.setString(8, null);
				prstmt.setDate(9, new java.sql.Date(((Fresher) can).getGraduation_date().getTime()));
				prstmt.setString(10, ((Fresher) can).getGraduation_rank());
				prstmt.setString(11, ((Fresher) can).getEducation());
				prstmt.setString(12, null);
				prstmt.setString(13, null);
				prstmt.setString(14, null);
			}
			if (can instanceof Intern) {
				prstmt.setString(7, null);
				prstmt.setString(8, null);
				prstmt.setString(9, null);
				prstmt.setString(10, null);
				prstmt.setString(11, null);
				prstmt.setString(12, ((Intern) can).getMajors());
				prstmt.setString(13, ((Intern) can).getSemester());
				prstmt.setString(14, ((Intern) can).getUniversity_name());
			}

			BangCapDAO.insertMBC(conn, can.bangCapList);

			int numberRecords = prstmt.executeUpdate();

			if (numberRecords != 0) {
				System.out.println("Insert successfully!");
			} else {
				System.out.println("Insert failed!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Insert Candidate that bai");
		}
		return candidate;
	}

	/**
	 * insert data
	 */
	public static void insertCandidate() {
		Candidate c;
		int candidate_type = Validator.isValidType(sc);
		if (candidate_type == 0) {
			c = new Experience();
			c.setCandidate_type(candidate_type);
			c.input(true);
			System.out.println("****");
		} else if (candidate_type == 1) {
			c = new Fresher();
			c.setCandidate_type(candidate_type);
			c.input(true);
			System.out.println("****");
		} else {
			c = new Intern();
			c.setCandidate_type(candidate_type);
			c.input(true);
			System.out.println("****");
		}
		insertCandidateGetCandidateID(c);
		Candidate.canidate_count++;
		System.out.println("Số cột được insert là : " + Candidate.canidate_count);
	}

	/**
	 * Hiển thị thông tin Candidate Nhập đúng số theo gợi ý để khỏi phải nhập lại
	 */
	public static void select() {

		int candidate_type = Validator.isValidType(sc);
		if (candidate_type == 0) {
			ExperienceDAO.selectExperience();
		}
		if (candidate_type == 1) {
			FresherDAO.selectFresher();
		}
		if (candidate_type == 2) {
			InternDAO.selectIntern();
		}
	}

	/**
	 * Phân biệt sự giống nhau và khác nhau của String và String Buffer
	 * 
	 * @param list
	 * @return
	 */
	public static String fullNameString_E(Set<Experience> list) {

		String s = "";
		for (Candidate candidate : list) {
			s += candidate.getFullName() + ",";
		}
		s = s.substring(0, s.length() - 1);
		return s;
	}

	/**
	 * @param list
	 * @return
	 */
	public static StringBuffer fullNameStringB_E(Set<Experience> list) {

		StringBuffer s = new StringBuffer();
		for (Candidate candidate : list) {
			s.append(candidate.getFullName()).append(", ");
		}
		s.delete(s.length() - 2, s.length());
		return s;
	}

//	public static void main(String[] args) {
//
//		Set<Experience> list = ExperienceDAO.selectEx();
//		StringBuffer str = fullNameStringB_E(list);
//		System.out.println(str);
//
//	}

	/**
	 * so sánh cách dùng String và String Buffer
	 */
	public static void soSanhString_Buffer() {

		Set<Experience> candidates = ExperienceDAO.selectEx();
		String string = fullNameString_E(candidates);
		System.out.println("Hiển thị tên sử dụng String : " + string);

		StringBuffer s = fullNameStringB_E(candidates);
		System.out.println("Hiển thị tên sử dụng StringBuffer : " + s);
		System.out.println("\n------------------------");
	}

	/**
	 * sắp xếp candidate
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static void sortCandidate() {

		int candidate_type = Validator.isValidType(sc);
		if (candidate_type == 0) {
			ExperienceDAO.selectExperience();
			;
		}
		if (candidate_type == 1) {
			FresherDAO.selectFresher();
			;
		}
		if (candidate_type == 2) {
			InternDAO.selectIntern();
			;
		}
	}

	/**
	 * THINHNV30 1993-03-03 XÓA THÔNG TIN HÀNH KHÁCH
	 */
	public static void deleteAll(String candidateID) {

		try (Connection connection = ConnectionUtil.getConnection();
				// Xóa hành khách chưa tiến hành test Covid (TestDate có giá trị null)
				PreparedStatement preparedStatement1 = connection
						.prepareStatement("DELETE FROM CANDIDATE WHERE candidateID = ?");
				PreparedStatement preparedStatement2 = connection
						.prepareStatement("DELETE FROM BangCap WHERE candidateID = ?")) {
			preparedStatement1.setString(1, candidateID);
			preparedStatement2.setString(1, candidateID);
			preparedStatement1.executeUpdate();
			preparedStatement2.executeUpdate();

			// Đóng kết nối
//			connection.close();

			System.out.println("Deleted CANDIDATE !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// 10.Hien thi tong so hoc sinh
	public static int selectSLHS() {

		int n = 0;
		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con.prepareStatement(
						"select count('x') as so_luong_ung_vien\r\n"
						+ "from CANDIDATE ")) {

			ResultSet rs = prst.executeQuery();
			while (rs.next()) {

				n = rs.getInt("so_luong_ung_vien");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi truy vấn");
			e.printStackTrace();
		}
		return n;
	}
	
	public static void selectSLHS_Cap() {

		int n = 0;
		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con.prepareStatement(
						"select candidate_type,count('x') soLuong\r\n"
						+ "from CANDIDATE\r\n"
						+ "group by candidate_type\r\n"
						+ "order by candidate_type ")) {

			ResultSet rs = prst.executeQuery();
			if (rs.next()) {

				System.out.println("candidate_type\t\tsoLuong");

				do {

					n = rs.getInt("candidate_type");

					String soLuong = rs.getString("soLuong");

					System.out.println(n + "\t\t\t" + soLuong);

				} while (rs.next());

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi truy vấn");
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
//		System.out.println(selectSLHS());
		selectSLHS_Cap();
	}
}
