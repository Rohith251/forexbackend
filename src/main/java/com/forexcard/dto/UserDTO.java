package com.forexcard.dto;

public class UserDTO {
	
	private String name;
	private String adminAction;
	private String cardStatus;
    private String cardNumber;
    
    
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserDTO(String name, String adminAction, String cardStatus, String cardNumber) {
		super();
		this.name = name;
		this.adminAction = adminAction;
		this.cardStatus = cardStatus;
		this.cardNumber = cardNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAdminAction() {
		return adminAction;
	}


	public void setAdminAction(String adminAction) {
		this.adminAction = adminAction;
	}


	public String getCardStatus() {
		return cardStatus;
	}


	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", adminAction=" + adminAction + ", cardStatus=" + cardStatus + ", cardNumber="
				+ cardNumber + "]";
	}
	
	
	
	
	
	

}
