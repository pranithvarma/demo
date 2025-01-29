package com.practice.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class ErrorResponse {
	private LocalDateTime date;
	private String message;
	private  int status;
	public ErrorResponse(LocalDateTime date, int status, String message) {
		super();
		this.date = date;
		this.message = message;
		this.status = status;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
