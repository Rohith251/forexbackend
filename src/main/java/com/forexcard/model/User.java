package com.forexcard.model;

import java.time.LocalDate;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private LocalDate dob;
    private String gender;
    private String address;
    private String state;
    private String country;
    private String email;
    private Long phonenumber;
    private String password;
    private String role = "user";
    private double salary;
    private String pan;
    private String adminAction;

    @Lob
    @Column(name="salary_slip")
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore
    private byte[] salarySlip;
    

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ForexCard forexCard;

    // Constructors, getters, and setters

    public User() {
        super();
    }

	public User(Integer id, String name, LocalDate dob, String gender, String address, String state, String country,
			String email, Long phonenumber, String password, String role, double salary, String pan, String adminAction,
			byte[] salarySlip, ForexCard forexCard) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.state = state;
		this.country = country;
		this.email = email;
		this.phonenumber = phonenumber;
		this.password = password;
		this.role = role;
		this.salary = salary;
		this.pan = pan;
		this.adminAction = adminAction;
		this.salarySlip = salarySlip;
		this.forexCard = forexCard;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public byte[] getSalarySlip() {
		return salarySlip;
	}

	public void setSalarySlip(byte[] salarySlip) {
		this.salarySlip = salarySlip;
	}

	public ForexCard getForexCard() {
		return forexCard;
	}

	public void setForexCard(ForexCard forexCard) {
		this.forexCard = forexCard;
	}

   
}

   