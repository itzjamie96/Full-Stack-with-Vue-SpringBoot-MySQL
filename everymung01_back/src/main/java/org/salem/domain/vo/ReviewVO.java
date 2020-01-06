package org.salem.domain.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewVO {
	
	private int reviewNo;
	private int paymentNo; //insert 할 때 paymentNo 같이 넣기 위해서 추가
	private String reviewContent;
	private int stars;
	private String userName;
	private Date reviewDate;
	
}
