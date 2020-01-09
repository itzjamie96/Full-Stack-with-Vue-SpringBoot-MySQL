package org.salem.domain.vo;


import java.sql.Date;

import lombok.Data;

@Data
public class SitterVO {
	
	private int sitterNo;
	private int sittingNo;
	private String sittingType;
	private String sitterEmail;
	private String sitterName;
	private String sitterPw;
	private String sitterPhone;
	private String sitterAddress;
	private String sitterImg1;
	private String sitterImg2;
	private String sitterImg3;
	private String sitterImg4;
	private String sitterImg5;
	private String possibleDay; //어떤식으로 입력받아 저장할지, 숫자/문자  => ,로 이어서 저장.
	private boolean sitterGender;
	private int sitterAge;
	private String profile;
	private String petProfile;
	private String identityCheck;
	private String qualificationCheck;
	private boolean approvalStatus;
	private String approvalDate;
	private String profileTitle;
	private double lat;
	private double lng;
}
