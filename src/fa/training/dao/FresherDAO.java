package fa.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fa.training.entities.BangCap;
import fa.training.entities.Fresher;
import fa.training.entities.Fresher;
import fa.training.utils.BirthDayException;
import fa.training.utils.ConnectionUtil;
import fa.training.utils.EmailException;

public class FresherDAO {

	/**
	 * Methods PrepareStatement selectFresher
	 * 
	 * @return
	 */
	public static Set<Fresher> selectFr() {

		Set<Fresher> danhSachEx = new HashSet<Fresher>();

		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con.prepareStatement("select * from Candidate where Candidate_type = '1' ")) {
			ResultSet rs = prst.executeQuery();

			while (rs.next()) {

				Fresher ex = new Fresher();

				ex.setCandidate_type(rs.getInt("candidate_type"));
				ex.setCandidateID(rs.getString("CandidateID"));
				ex.setFullName(rs.getString("fullname"));
				ex.setBirthDay(rs.getDate("BirthDay"));
				ex.setEmail(rs.getString("Email"));
				ex.setPhone(rs.getString("Phone"));
				ex.setGraduation_date(rs.getDate("graduation_date"));
				ex.setGraduation_rank(rs.getString("Graduation_rank"));
				ex.setEducation(rs.getString("Education"));

				ex.setBangCapList(BangCapDAO.selectBangCap(ex.getCandidateID()));

				danhSachEx.add(ex);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi truy vấn");
			e.printStackTrace();
		}
		return danhSachEx;
	}

	/**
	 * Hiển thị danh sách Fresher
	 */
	public static void selectFresher() {

		Set<Fresher> hsList = selectFr();
		List<Fresher> hsListconvert = new ArrayList<>(hsList);
		System.out.println("============Hiển thị danh sách Fresher ==========");

		if (hsListconvert.size() == 0) {
			System.out.println("Không có thông tin của Fresher :");
		} else {
			System.out.println("=====Thông tin đã tìm thấy=========");
			Collections.sort(hsListconvert, Comparator.comparing(Fresher::getCandidateID).reversed());
			for (Fresher Fresher : hsListconvert) {
				System.out.println(Fresher.showInfo());
			}
			
		}
		System.out.println("\n----------------------------------------------------------");
	}

	public static void main(String[] args) {
		selectFresher();
	}

}
