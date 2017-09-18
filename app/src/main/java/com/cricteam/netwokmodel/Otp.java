package com.cricteam.netwokmodel;


public class Otp {

	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}


	public String getCreated_time() {
		return created_time;
	}


	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}



	private int id;
	

	private String mobileNo;
	
	private String otp;
	private String created_time;
	
	public Otp(){
	
	}
	public Otp(String mobileNo, String otp, String created_time) {
		super();
		this.mobileNo = mobileNo;
		this.otp = otp;
		this.created_time = created_time;
	}


	@Override
	public String toString(){
		 return "{id:" + id + ", mobileNo:" + mobileNo + ", otp:"
				+ otp + ", createdTime:" + created_time + "}";
	}
}
