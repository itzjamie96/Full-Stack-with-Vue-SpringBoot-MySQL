package org.salem.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestVO {

	private int count;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userAddress;
	private int paymentNo;
	private String startTime;
	private String endTime;
	private String sitterName;
	private String sitterPhone;
	private String sittingType;
	
}
