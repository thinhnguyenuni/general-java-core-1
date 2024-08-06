package fa.training.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import fa.training.utils.Validator;

public class Intern extends Candidate  {

	private String majors;
	private String semester;
	private String university_name;

	public Intern() {
		super();
	}

	public Intern(String candidateID, String fullName, Date birthDay, String phone, String email, int candidate_type,
			ArrayList<BangCap> bangCapList, String majors, String semester, String university_name) {
		super(candidateID, fullName, birthDay, phone, email, candidate_type, bangCapList);
		this.majors = majors;
		this.semester = semester;
		this.university_name = university_name;
	}

	public String getMajors() {
		return majors;
	}

	public void setMajors(String majors) {
		this.majors = majors;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getUniversity_name() {
		return university_name;
	}

	public void setUniversity_name(String university_name) {
		this.university_name = university_name;
	}

	@Override
	public String showInfo() {
		return super.toString() + showMe();
	}

	public String showMe() {
		return ",Majors = " + majors + ",Semester = " + semester + ",University_name = " + university_name + "]";
	}

	/**
	 * Thêm thông tin Experience Chú ý nhập đúng theo yêu cầu nếu không sẽ phải nhập
	 * lại
	 */
	public void input(boolean isInsertFlg) {
		super.input(isInsertFlg);
		Scanner sc = new Scanner(System.in);

		// NHẬP Majors
		System.out.println("Nhập Majors : ");
		this.setMajors(sc.nextLine().toUpperCase());

		// NHẬP Semester
		System.out.println("Nhập Semester : ");
		this.setSemester(sc.nextLine().toUpperCase());

		// NHẬP University_name
		System.out.println("Nhập University_name : ");
		this.setUniversity_name(sc.nextLine().toUpperCase());

		// NHẬP SỐ BẰNG CẤP
		ArrayList<BangCap> cerList = new ArrayList<BangCap>();
		System.out.println("Nhập số lượng bằng cấp của Candidate : ");
		int soBC = Validator.isValidMore0(sc);
		
		for (int i = 0; i < soBC; i++) {
			BangCap cer = new BangCap();
			cerList.add(cer.insertBC(this.getCandidateID()));
		}
		this.setBangCapList(cerList);

	}



//	@Override
//	public int compareTo(Intern o) {
//		return o.getBirthDay().compareTo(this.getBirthDay());
//	}

}
