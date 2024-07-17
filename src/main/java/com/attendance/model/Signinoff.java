package com.attendance.model;


public class Signinoff {
	
	private int id;
	private int userId;
	
	private String signdatetime;

	
	private String signintime;

	private String signouttime;
	
	private int groupflag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSigndatetime() {
		return signdatetime;
	}

	public void setSigndatetime(String signdatetime) {
		this.signdatetime = signdatetime;
	}

	public String getSignintime() {
		return signintime;
	}

	public void setSignintime(String signintime) {
		this.signintime = signintime;
	}

	public String getSignouttime() {
		return signouttime;
	}

	public void setSignouttime(String signouttime) {
		this.signouttime = signouttime;
	}

	public int getGroupflag() {
		return groupflag;
	}

	public void setGroupflag(int groupflag) {
		this.groupflag = groupflag;
	}

	public Signinoff(int id, int userId, String signdatetime, String signintime, String signouttime, int groupflag) {
		super();
		this.id = id;
		this.userId = userId;
		this.signdatetime = signdatetime;
		this.signintime = signintime;
		this.signouttime = signouttime;
		this.groupflag = groupflag;
	}

	public Signinoff() {
		super();
	}

	@Override
	public String toString() {
		return "Signinoff [id=" + id + ", userId=" + userId + ", signdatetime=" + signdatetime + ", signintime="
				+ signintime + ", signouttime=" + signouttime + "]";
	}

	
}
