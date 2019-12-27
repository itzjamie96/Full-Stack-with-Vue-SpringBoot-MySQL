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
	private String userName;
	private String userAddress;
	private int petNo;        // paymentList불러올 때, 두마리 예약 시 두개를 불러오기 때문에 필요
	private String petName;   //
	private String dogBreed;  //
	private String size;      //
	private List<PetInfoVO> petDetailList;  //위의 펫 정보 List로 저장
	
	//reservaionVO
	private Boolean refundStatus; 
	private Boolean reviewStatus;
	private Boolean paymentStatus;
}
