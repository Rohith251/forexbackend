package com.forexcard.dto;

import java.time.LocalDate;

public class PendingUserDTO {
	
	 private Integer id;
	    private String name;
	    private LocalDate dob;
	    private String gender;
	    private String address;
	    private String state;
	    private String country;
	    private String email;
	    private Long phonenumber;
	    private double salary;
	    private String pan;
	    private String adminAction;
	    
	    

	    public PendingUserDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

		// Constructors, Getters, and Setters
	    public PendingUserDTO(Integer id, String name, LocalDate dob, String gender, String address, String state, 
	                   String country, String email, Long phonenumber, double salary, String pan, String adminAction) {
	        this.id = id;
	        this.name = name;
	        this.dob = dob;
	        this.gender = gender;
	        this.address = address;
	        this.state = state;
	        this.country = country;
	        this.email = email;
	        this.phonenumber = phonenumber;
	        this.salary = salary;
	        this.pan = pan;
	        this.adminAction = adminAction;
	    }

	    // Getters and Setters
	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public LocalDate getDob() {
	        return dob;
	    }

	    public void setDob(LocalDate dob) {
	        this.dob = dob;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
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

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public Long getPhonenumber() {
	        return phonenumber;
	    }

	    public void setPhonenumber(Long phonenumber) {
	        this.phonenumber = phonenumber;
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

	    public String getAdminAction() {
	        return adminAction;
	    }

	    public void setAdminAction(String adminAction) {
	        this.adminAction = adminAction;
	    }
	    
}