package fa.training.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import fa.training.utils.Validator;

public class Fresher extends Candidate  {

	private Date graduation_date;
	private String graduation_rank;
	private String education;

	public Fresher() {
		super();
	}

	public Fresher(String candidateID, String fullName, Date birthDay, String phone, String email, int candidate_type,
			ArrayList<BangCap> bangCapList, Date graduation_date, String graduation_rank, String education) {
		super(candidateID, fullName, birthDay, phone, email, candidate_type, bangCapList);
		this.graduation_date = graduation_date;
		this.graduation_rank = graduation_rank;
		this.education = education;
	}

	public Date getGraduation_date() {
		return graduation_date;
	}

	public void setGraduation_date(Date graduation_date) {
		this.graduation_date = graduation_date;
	}

	public String getGraduation_rank() {
		return graduation_rank;
	}

	public void setGraduation_rank(String graduation_rank) {
		this.graduation_rank = graduation_rank;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Override
	public String showInfo() {
		return super.toString() + showMe();
	}

	public String showMe() {
		return ",Graduation_date" + graduation_date + ",Graduation_rank = " + graduation_rank + ",Education = "
				+ education + "]";
	}

	/**
	 * Thêm thông tin Fresher Chú ý nhập đúng theo yêu cầu nếu không sẽ phải nhập
	 * lại
	 */

	public void input(boolean isInsertFlg) {
		super.input(isInsertFlg);
		Scanner sc = new Scanner(System.in);

		// NHẬP Graduation_date
		System.out.println("Nhập Graduation_date (phải nhập đúng định dạng yyyy-MM-DD): ");
		Date graduation_date = Validator.isValidDay(sc);
		this.setGraduation_date(graduation_date);

		// NHẬP Graduation_rank
		System.out.println("Nhập Graduation_rank : ");
		this.setGraduation_rank(sc.nextLine().toUpperCase());

		// NHẬP Education
		System.out.println("Nhập Education : ");
		this.setEducation(sc.nextLine().toUpperCase());

		// NHẬP SỐ BẰNG CẤP
		ArrayList<BangCap> cerList = new ArrayList();
		System.out.println("Nhập số lượng bằng cấp của Candidate : ");
		int sobc = Validator.isValidMore0(sc);
		
		for (int i = 0; i < sobc; i++) {
			BangCap cer = new BangCap();
			cerList.add(cer.insertBC(this.getCandidateID()));
		}
		this.setBangCapList(cerList);
	}




//	public int compareTo(Fresher o) {
//		return o.getBirthDay().compareTo(this.getBirthDay());
//	}

}
