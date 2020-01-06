package org.salem.domain.vo;

import java.util.Date;
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
	private String petName;   //수정할것
	private String dogBreed;  //수정할것
	private String size;      //수정할것
	private List<PetInfoVO> petDetailList;
	
	//reservaionVO
	private Boolean refundStatus; 
	private Boolean reviewStatus;
	private Boolean paymentStatus;
}
