package fa.training.main;

import java.sql.SQLException;
import java.util.Scanner;

import fa.training.dao.BangCapDAO;
import fa.training.dao.CandidateDAO;
import fa.training.services.Methods;

public class Menu {

	public void menu() throws SQLException {

		Scanner sc = new Scanner(System.in);
		String st = null;
		int choice;
		do {
			System.out.println("----- Chào mừng bạn đến với chương trình quản lí ứng viên -----");
			System.out.println("----- Các chức năng chính trong chương trình -----");
			System.out.println("1. Thêm thông tin ứng viên ");
			System.out.println("2. Hiển thị thông tin tấc cả các ứng viên ứng viên ");
			System.out.println("3. Hiển thị thông tin các ứng viên ứng viên theo nhóm ");
			System.out.println("4. Hiển thị thông tin bằng cấp ");
			System.out.println("5. Cập nhật thông tin ứng viên sử dụng resault set ");
			System.out.println("6. Thêm thông tin ứng viên sử dụng resault set ");
			System.out.println("7. Phân biệt String và String Buffer ");
			System.out.println("8. Sắp xếp Candidate theo candidate_type và cadidateid ");
			System.out.println("9. Update một cột tùy ý ");
			System.out.println("10. Xóa Candidate theo candidateID. ");
			System.out.println("0. Thoát");
			System.out.println("===========Vui lòng chọn chức năng================= ");
			System.out.println("Nhập vào số nguyên: ");

			while (true) {
				try {
					st = sc.nextLine();
					choice = Integer.parseInt(st);
					System.out.println("Số bạn nhập là : " + choice);
					break;
				} catch (NumberFormatException ex) {
					System.out.println(st + ":  Không phải số nguyên");
					System.out.println("Vui lòng nhập lại ");
				}
			}

			switch (choice) {

			case 1:
				// 1. Thêm thông tin ứng viên

				Methods.insertCandidate();

				break;

			case 2:

				// 2. Hiển thị thông tin tấc cả các ứng viên ứng viên
				CandidateDAO.getCandidatesFromDatabase();

				break;

			case 3:

				// 3. Hiển thị thông tin các ứng viên ứng viên
				Methods.select();

				break;

			case 4:

				// 4. Hiển thị thông tin bằng cấp
				BangCapDAO.selectBangCap();
				break;

			case 5:
				// 5. Cập nhật thông tin ứng viên sử dụng resault set

				CandidateDAO.updateCandidate();

				break;

			case 6:
				// 6. Thêm thông tin ứng viên sử dụng resault set

				CandidateDAO.insertCDR();
				break;

			case 7:
				// 7. So sánh string và string-buffer

				Methods.soSanhString_Buffer();
				break;

			case 8:
				// 8. Sắp xếp Candidate theo năm sinh giảm dần

				Methods.sortCandidate();
				break;

			case 9:
				// 9. Update một cột tùy ý

				CandidateDAO.updateOneRow();
				break;

			case 10:
				// 9. Xóa Candidate theo candidateID.

				System.out.println("Nhập CandidateID muốn xóa ! ");
				String candidateID = sc.nextLine();
				Methods.deleteAll(candidateID);
				break;

			case 0:
				// 0. Thoát
				System.out.println("====Kết thúc chương trình======");
				break;

			default:
				System.out.println("Lựa chọn không hợp lệ");
				break;
			}
		} while (choice != 0);

	}

	public static void main(String[] args) {

		try {
			Menu main = new Menu();
			main.menu();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("The system has encountered an unexpected problem, sincerely sorry");
			System.out.println("Program is Exit");
		}

	}

}
