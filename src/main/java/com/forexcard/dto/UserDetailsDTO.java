package com.forexcard.dto;

import java.time.LocalDate;

public class UserDetailsDTO {
	private String address;
    private String state;
    private String country;
    private String gender;
    private Long phonenumber;
    private LocalDate dob;
    private double salary;
    private String pan;
    
    
    
    
    
	public UserDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDetailsDTO(String address, String state, String country, String gender, Long phonenumber, LocalDate dob,
			double salary, String pan) {
		super();
		this.address = address;
		this.state = state;
		this.country = country;
		this.gender = gender;
		this.phonenumber = phonenumber;
		this.dob = dob;
		this.salary = salary;
		this.pan = pan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(Long phonenumber) {
		this.phonenumber = phonenumber;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
    
    

}
