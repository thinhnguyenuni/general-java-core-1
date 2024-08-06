package fa.training.entities;

import java.util.Date;
import java.util.Scanner;

import fa.training.utils.Validator;

public class BangCap {

	private String candidateID;
	private String certificatedID;
	private String certificateName;
	private String certificateRank;
	private Date certificatedDate;

	public BangCap() {
		
	}

	public BangCap(String candidateID, String certificatedID, String certificateName, String certificateRank,
			Date certificatedDate) {
		this.candidateID = candidateID;
		this.certificatedID = certificatedID;
		this.certificateName = certificateName;
		this.certificateRank = certificateRank;
		this.certificatedDate = certificatedDate;
	}

	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public String getCertificatedID() {
		return certificatedID;
	}

	public void setCertificatedID(String certificatedID) {
		this.certificatedID = certificatedID;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateRank() {
		return certificateRank;
	}

	public void setCertificateRank(String certificateRank) {
		this.certificateRank = certificateRank;
	}

	public Date getCertificatedDate() {
		return certificatedDate;
	}

	public void setCertificatedDate(Date certificatedDate) {
		this.certificatedDate = certificatedDate;
	}

	@Override
	public String toString() {
		return "BangCap [CandidateID=" + candidateID + ", CertificatedID=" + certificatedID + ", CertificateName="
				+ certificateName + ", CertificateRank=" + certificateRank + ", CertificatedDate=" + certificatedDate
				+ "]";
	}

	public static BangCap insertBC(String id) {

		// 1. Thêm thông tin

		BangCap bangCap = new BangCap();
		System.out.println("----- Thêm thông bằng cấp -----");
		Scanner sc = new Scanner(System.in);

		// NHẬP CandidateID
		bangCap.setCandidateID(id);

		// NHẬP CertificatedID
		System.out.println("Nhập CertificatedID (ví dụ CF0009) ");
		String CertificatedID = Validator.isValidCfID(sc);
		bangCap.setCertificatedID(CertificatedID);// flag=true;
		
		
		// NHẬP CertificateName
		System.out.println("Nhập CertificateName : ");
		bangCap.setCertificateName(sc.nextLine().toUpperCase());

		// NHẬP CertificateRank
		System.out.println("Nhập CertificateRank : ");
		bangCap.setCertificateRank(sc.nextLine().toUpperCase());

		// NHẬP CertificatedDate
		System.out.println("Nhập CertificatedDate (phải nhập đúng định dạng yyyy-MM-DD): ");
		Date certificatedDate = Validator.isValidDay(sc);
		bangCap.setCertificatedDate(certificatedDate);

		return bangCap;
	}

}
