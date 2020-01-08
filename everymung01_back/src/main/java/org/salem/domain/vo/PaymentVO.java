package org.salem.domain.vo;

import java.util.List;

import lombok.Data;

@Data
public class PaymentVO {
	
	private int paymentNo;
	private int sitterNo;
	private String sittingType;
	private String sitterName;
	private String sitterPhone;
	private String sitterAddress;
	private String startTime;
	private String endTime;
	private String request;
	private String paymentMethod;
	private String paymentDate;
	private int amount;
	private int petNo;
	private String userName;
	private String userAddress;

	private String petName;   
	private String dogBreed;  
	private String size;      

	private int dayPrice;
	private int HomePrice;

	private List<PetInfoVO> petDetailList;
	
	//reservaionVO
	private Boolean refundStatus; 
	private Boolean reviewStatus;
	private Boolean paymentStatus;
	
	private String tid;	
}
