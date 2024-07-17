package com.attendance.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class SignDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	@CreatedDate
	private LocalDateTime signindatetime;
	@LastModifiedDate
	private LocalDateTime signoutdatetime;
	public SignDetail() {
		super();
	}

	public SignDetail(int id, int userId, LocalDateTime signindatetime, LocalDateTime signoutdatetime) {
		super();
		this.id = id;
		this.userId = userId;
		this.signindatetime = signindatetime;
		this.signoutdatetime = signoutdatetime;
	}

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



	public LocalDateTime getSignindatetime() {
		return signindatetime;
	}



	public void setSignindatetime(LocalDateTime signindatetime) {
		this.signindatetime = signindatetime;
	}



	public LocalDateTime getSignoutdatetime() {
		return signoutdatetime;
	}



	public void setSignoutdatetime(LocalDateTime signoutdatetime) {
		this.signoutdatetime = signoutdatetime;
	}



	@Override
	public String toString() {
		return "SignDetail [id=" + id + ", userId=" + userId + ", signindatetime=" + signindatetime
				+ ", signoutdatetime=" + signoutdatetime + "]";
	}
	
	

}
