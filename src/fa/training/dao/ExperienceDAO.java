package fa.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fa.training.entities.Experience;
import fa.training.utils.ConnectionUtil;

public class ExperienceDAO {

	/**
	 * Hien thi danh sach Experience
	 * 
	 * @return
	 */
	public static Set<Experience> selectEx() {

		Set<Experience> danhSachEx = new HashSet<Experience>();

		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con.prepareStatement("select * from Candidate where Candidate_type = '0' ")) {

			ResultSet rs = prst.executeQuery();
			while (rs.next()) {

				Experience ex = new Experience();

				ex.setCandidate_type(rs.getInt("candidate_type"));
				ex.setCandidateID(rs.getString("CandidateID"));
				ex.setFullName(rs.getString("fullname"));
				ex.setBirthDay(rs.getDate("BirthDay"));
				ex.setEmail(rs.getString("Email"));
				ex.setPhone(rs.getString("Phone"));
				ex.setProSkill(rs.getString("ProSkill"));
				ex.setExpInYear(rs.getInt("ExpInYear"));

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
	 * Hiển thị danh sách Experience
	 */
	public static void selectExperience() {

		System.out.println("============Hiển thị danh sách Experience ==========");
		// tạo list để hứng kết quả
		Set<Experience> hsList = selectEx();
		List<Experience> hsListconvert = new ArrayList<>(hsList);
		if (hsList.size() == 0) {
			System.out.println("không có thông tin của Experience :");
		} else {
			System.out.println("=====Thông tin đã tìm thấy=========");
//			Collections.sort(hsList, new Comparator<Experience>() {
//	            @Override
//	            public int compare(Experience o1, Experience o2) {
//	                return o1.getCandidateID().compareTo(o2.getCandidateID());
//	            }
//	        });
			Collections.sort(hsListconvert, Comparator.comparing(Experience::getCandidateID));
			for (Experience experience : hsListconvert) {
				System.out.println(experience.showInfo());
			}
		}
		System.out.println("\n----------------------------------------------------------");
	}

	
}
