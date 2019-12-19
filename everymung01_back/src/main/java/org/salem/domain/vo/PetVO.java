package org.salem.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class PetVO  extends PetInfoVO{
	private String userName;   //견주이름 
	private String dogBreed;    //견종 
	private String breedInfo;     //견종 정보 
	private String petGender;     // 반려견 성별 
	private float petWeight;      // 반려견 체중 
	private int petAge;           // 반려견 나이 
	private boolean neutralization;    // 반려견 중성화 여부 
	private String petAnswer;          // 반려견 답변 
	private String petImg;             //반려견 이미지 

	
	

}
