package fa.training.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import fa.training.utils.Validator;

public class Experience extends Candidate  {
	
	private String proSkill;
	private int expInYear;

	public Experience() {
		super();
	}

	public Experience(String candidateID, String fullName, Date birthDay, String phone, String email,
			int candidate_type, ArrayList<BangCap> bangCapList, String proSkill, int expInYear) {
		super(candidateID, fullName, birthDay, phone, email, candidate_type, bangCapList);
		this.proSkill = proSkill;
		this.expInYear = expInYear;
	}

	public String getProSkill() {
		return proSkill;
	}

	public void setProSkill(String proSkill) {
		this.proSkill = proSkill;
	}

	public int getExpInYear() {
		return expInYear;
	}

	public void setExpInYear(int expInYear) {
		this.expInYear = expInYear;
	}

	@Override
	public String showInfo() {
		return super.toString() + showMe();
	}

	public String showMe() {
		return ",ProSkill = " + proSkill + ",ExpInYear" + expInYear + "]";
	}

	/**
	 * Thêm thông tin Experience Chú ý nhập đúng theo yêu cầu nếu không sẽ phải nhập
	 * lại
	 * 
	 * @param isInsertFlg
	 */

	public void input(boolean isInsertFlg) {

		super.input(isInsertFlg);
		Scanner sc = new Scanner(System.in);

		// NHẬP ProSkill
		System.out.println("Nhập ProSkill : ");
		this.setProSkill(sc.nextLine().toUpperCase());

		// NHẬP expInYear
		System.out.println("Nhập expInYear : ");
		int expInYear = Validator.isValidMore0(sc);
		this.setExpInYear(expInYear);
		
		
		// NHẬP SỐ BẰNG CẤP
		ArrayList<BangCap> cerList = new ArrayList<BangCap>();
		System.out.println("Nhập số lượng bằng cấp của Candidate : ");
		int sobc =Validator.isValidMore0(sc);

		for (int i = 0; i < sobc; i++) {
			BangCap cer = new BangCap();
			cerList.add(cer.insertBC(this.getCandidateID()));
		}
		this.setBangCapList(cerList);

	}



//	@Override
//	public int compareTo(Experience exp) {
//		return exp.getBirthDay().compareTo(this.getBirthDay());
//	}

}
