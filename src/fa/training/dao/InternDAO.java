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
import fa.training.entities.Intern;
import fa.training.entities.Intern;
import fa.training.utils.BirthDayException;
import fa.training.utils.ConnectionUtil;
import fa.training.utils.EmailException;

public class InternDAO {

	/**
	 * Hien thi danh sach Intern Methods PrepareStatement selectIntern
	 * 
	 * @return
	 */
	public static Set<Intern> selectIn() {

		Set<Intern> danhSachEx = new HashSet<Intern>();

		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con.prepareStatement("select * from Candidate where Candidate_type = '2' ")) {
			ResultSet rs = prst.executeQuery();

			while (rs.next()) {

				Intern ex = new Intern();

				ex.setCandidate_type(rs.getInt("candidate_type"));
				ex.setCandidateID(rs.getString("CandidateID"));
				ex.setFullName(rs.getString("fullname"));
				ex.setBirthDay(rs.getDate("BirthDay"));
				ex.setEmail(rs.getString("Email"));
				ex.setPhone(rs.getString("Phone"));
				ex.setMajors(rs.getString("Majors"));
				ex.setSemester(rs.getString("Semester"));
				ex.setUniversity_name(rs.getString("University_name"));

				ex.setBangCapList(BangCapDAO.selectBangCap(ex.getCandidateID()));
				// .....
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
	 * Hiển thị danh sách Intern
	 */
	public static void selectIntern() {

		System.out.println("============Hiển thị danh sách Intern ==========");

		// tạo list để hứng kết quả
		Set<Intern> hsList = selectIn();
		List<Intern> hsListconvert = new ArrayList<>(hsList);
		if (hsList.size() == 0) {
			System.out.println("không có thông tin của Experience :");
		} else {
			System.out.println("=====Thông tin đã tìm thấy=========");

			Collections.sort(hsListconvert, Comparator.comparing(Intern::getCandidateID).reversed());
			for (Intern Intern : hsListconvert) {
				System.out.println(Intern.showInfo());
			}
		}
		System.out.println("\n----------------------------------------------------------");
	}

	public static void main(String[] args) {
		selectIntern();
	}

}
