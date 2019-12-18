package org.salem.domain.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PaymentVO {
	
	int paymentNo;
	int sitterNo;
	String sitterType;
	String sitterName;
	int sitterPhone;
	String sitterAddress;
	Date startTime;
	Date endTime;
	String request;
	String paymentMethod;
	int amount;
	String userName;
	String userAddress;
	List<PetInfoVO> petDetailList;
}
