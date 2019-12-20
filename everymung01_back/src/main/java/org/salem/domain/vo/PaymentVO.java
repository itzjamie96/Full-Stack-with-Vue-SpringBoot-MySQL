package org.salem.domain.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PaymentVO {
	
	int paymentNo;
	int sitterNo;
	String sittingType;
	String sitterName;
	String sitterPhone;
	String sitterAddress;
	String startTime;
	String endTime;
	String request;
	String paymentMethod;
	int amount;
	int petNo;
	String userName;
	String userAddress;
	String petName;
	String dogBreed;
	String size;
	List<PetInfoVO> petDetailList;
}
