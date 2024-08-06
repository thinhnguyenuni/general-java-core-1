package fa.training.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import fa.training.utils.BirthDayException;
import fa.training.utils.Validator;

public abstract class Candidate implements Comparable<Candidate>{
	private String candidateID;
	private String fullName;
	private Date birthDay;
	private String phone;
	private String email;
	private int candidate_type;
	public static int canidate_count;
	public ArrayList<BangCap> bangCapList;

	public Candidate() {
	}

	public Candidate(String candidateID, String fullName, Date birthDay, String phone, String email, int candidate_type,
			ArrayList<BangCap> bangCapList) {
		super();
		this.candidateID = candidateID;
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.phone = phone;
		this.email = email;
		this.candidate_type = candidate_type;
		this.bangCapList = bangCapList;
	}

	/**
	 * Hàm lấy giá trị CandidateID
	 * 
	 * @return
	 */
	public String getCandidateID() {
		return candidateID;
	}

	/**
	 * Hàm cập nhật giá trị CandidateID
	 * 
	 * @return
	 */
	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCandidate_type() {
		return candidate_type;
	}

	public void setCandidate_type(int candidate_type) {
		this.candidate_type = candidate_type;
	}

	public static int getCanidate_count() {
		return canidate_count;
	}

	public static void setCanidate_count(int canidate_count) {
		Candidate.canidate_count = canidate_count;
	}

	public ArrayList<BangCap> getBangCapList() {
		return bangCapList;
	}

	public void setBangCapList(ArrayList<BangCap> bangCapList) {
		this.bangCapList = bangCapList;
	}

	public abstract String showInfo();

	@Override
	public String toString() {
		String str = "Candidate [candidateID=" + candidateID + ", fullName=" + fullName + ", birthDay=" + birthDay
				+ ", phone=" + phone + ", email=" + email + ", candidate_type=" + candidate_type;
		if (bangCapList != null) {
			return str + ", bangCapList=" + bangCapList + "]";
		}

		return str + "]";
	}

	/**
	 * Nhập thông tin ứng viên chú ý nhập theo mẫu để khỏi phải nhập lại
	 * 
	 * @param isInsertFlg
	 * 
	 * @throws BirthDayException
	 */
	public void input(boolean isInsertFlg) {

		System.out.println("----- Thêm thông Candidate -----");
		Scanner sc = new Scanner(System.in);
		
		
		// NHẬP CandidateID
		String candidateID1 = Validator.isValidID(isInsertFlg, sc);
		this.setCandidateID(candidateID1);// flag=true;

		// NHẬP CertificateName
		System.out.println("Nhập FullName : ");
		this.setFullName(sc.nextLine().toUpperCase());

		// NHẬP BirthDay
		System.out.println("Nhập BirthDay (phải nhập đúng định dạng yyyy-MM-DD): ");
		Date birthDay = Validator.isValidDay(sc);
		this.setBirthDay(birthDay);

		// NHẬP Phone
		String phone = Validator.isValidPhone(sc);
		this.setPhone(phone);// flag=true;

		// NHẬP email
		String email = Validator.isValidEmail(sc);
		this.setEmail(email);// flag=true;

		// NHẬP Candidate_type
		int candidate_type = Validator.isValidType(sc);
		this.setCandidate_type(candidate_type);
		
	}

//	public int compareTo(Candidate can){
//		return -1*(can.getCandidateID().compareTo(this.getCandidateID()));
//	}
	
	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public int compareTo(Candidate can) {
		return can.getBirthDay().compareTo(this.getBirthDay());
	}
	
//	@Override
//    public int compareTo(Candidate o) {
//        if (this.getCandidate_type() != o.getCandidate_type()){
//            return this.getCandidate_type() - o.getCandidate_type();
//        }
//        return this.birthDay.compareTo(o.birthDay);
//    }
	
	@Override
	public int hashCode() {
		return Objects.hash(fullName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candidate other = (Candidate) obj;
		return Objects.equals(fullName, other.fullName);
	}

}