package fa.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fa.training.entities.BangCap;
import fa.training.utils.ConnectionUtil;

public class BangCapDAO {

	/**
	 * Insert bằng cấp
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static void insertMBC(Connection con, List<BangCap> bangCaplist) {

		int numberRecords = 0;
		try (PreparedStatement prstmt = con.prepareStatement(
				"INSERT INTO BangCap (candidateID,certificatedID,certificateName,certificateRank,certificatedDate) VALUES (?,?,?,?,?)")) {

			for (BangCap bc : bangCaplist) {

				prstmt.setString(1, bc.getCandidateID());
				prstmt.setString(2, bc.getCertificatedID());
				prstmt.setString(3, bc.getCertificateName());
				prstmt.setString(4, bc.getCertificateRank());
				prstmt.setDate(5, new java.sql.Date(bc.getCertificatedDate().getTime()));

				numberRecords = prstmt.executeUpdate();
				if (numberRecords == 0) {
					System.out.println("Insert bằng cấp thất bại");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Insert bằng cấp thất bại ");
		}
		System.out.println("Insert bằng cấp thành công ");
	}

	/**
	 * Thêm thông tin bằng cấp Chú ý nhập đúng theo yêu cầu nếu không sẽ phải nhập
	 * lại
	 */
	public static void selectBangCap() {

		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con.prepareStatement(
						"select bc.CandidateID,c.Fullname,bc.CertificateName  ,bc.CertificatedDate\r\n"
								+ "from Candidate c\r\n" + "join BangCap bc \r\n"
								+ "on c.CandidateID=bc.CandidateID")) {

			ResultSet rs = prst.executeQuery();
			if (rs.next()) {
				System.out.println("CandidateID \t Fullname \t\t CertificateName \t\t CertificatedDate");
				do {
					String candidateID = rs.getString("CandidateID");
					String fullname = rs.getString("Fullname");
					String certificateName = rs.getString("CertificateName");

					Date certificatedDate = new Date();
					certificatedDate = rs.getDate("CertificatedDate");

					System.out.println(candidateID + "\t\t\t" + fullname + "\t\t\t" + certificateName + "\t\t\t"
							+ certificatedDate);
				} while (rs.next());
			} else {
				System.out.println("Không có kết quả nào");
			}
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn");
			e.printStackTrace();
		}

	}

	/**
	 * phương thức cập nhật Bằng cấp sử dụng Result set
	 * 
	 * @param ex
	 */
	public static void updateBCResult_M(Connection con, BangCap bc, String candidateID, String certificatedID) {

		String sql = "SELECT * FROM BangCap where  candidateID=? and CertificatedID=?";
		try (PreparedStatement pstm = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE)) {

			pstm.setString(1, candidateID);
			pstm.setString(2, certificatedID);
			ResultSet rs = pstm.executeQuery();

			System.out.println("Liet ke result set bằng cấp de tham chieu ...");
			printRs(rs);

			// Buoc 6: Lap qua result set
			// Di chuyen toi vi tri truoc hang dau tien de vong lap while lam viec chinh xac
			rs.beforeFirst();

			// Buoc 7: Lay du lieu result set
			while (rs.next()) {
				// Lay du lieu boi su dung ten cot

				rs.updateString("CertificateName", bc.getCertificateName());
				rs.updateString("CertificateRank", bc.getCertificateRank());

				Date certificatedDate = new Date();
				certificatedDate = bc.getCertificatedDate();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = dateFormat.format(certificatedDate);
				rs.updateString("CertificatedDate", formattedDate);

				rs.updateRow();
			}
			System.out.println("Liet ke result set de hien thi bang cap moi ...");
			printRs(rs);

		} catch (SQLException se) {
			// Xu ly cac loi cho JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Xu ly cac loi cho Class.forName
			e.printStackTrace();
		}
		System.out.println("\nVietJack chuc cac ban hoc tot!");
	}// Ket thuc main

	/**
	 * hiển thị bằng cấp trước và sau khi update
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static void printRs(ResultSet rs) throws SQLException {

		// Bao dam rang chung ta bat dau tu hang dau tien
		rs.beforeFirst();
		while (rs.next()) {
			// Lay du lieu boi su dung ten cot
			String candidateID = rs.getString("candidateID");
			String certificatedID = rs.getString("certificatedID");
			String certificateName = rs.getString("certificateName");
			String certificateRank = rs.getString("certificateRank");
			Date certificatedDate = rs.getDate("certificatedDate");

			// Hien thi cac gia tri
			System.out.print("\ncandidateID: " + candidateID);
			System.out.print("\ncertificatedID: " + certificatedID);
			System.out.print("\ncertificateName: " + certificateName);
			System.out.print("\ncertificateRank: " + certificateRank);
			System.out.print("\ncertificatedDate: " + certificatedDate);
			System.out.print("\n=================");
		}
		System.out.println();
	}// Ket thuc printRs()

	/**
	 * hiển thị bằng cấp
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static ArrayList<BangCap> selectBangCap(String canditeId) {

		ArrayList<BangCap> bclist = new ArrayList<>();
		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con.prepareStatement("select * from BangCap where CandidateID = ? ")) {

			prst.setString(1, canditeId);
			ResultSet rs = prst.executeQuery();

			while (rs.next()) {

				BangCap bc = new BangCap();

				bc.setCandidateID(canditeId);
				bc.setCertificatedID(rs.getString("CertificatedID"));
				bc.setCertificateName(rs.getString("CertificateName"));
				bc.setCertificateRank(rs.getString("CertificateRank"));
				bc.setCertificatedDate(rs.getDate("CertificatedDate"));

				bclist.add(bc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi truy vấn");
			e.printStackTrace();
		}
		return bclist;
	}
}
