package org.salem.domain.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersVO {
	
	private int userNo;
	private String userEmail;
	private String userName;
	private String userPw;
	private String userPhone;
	private String userAddress;
	private String userProfile;
	private Date userDate;

}
