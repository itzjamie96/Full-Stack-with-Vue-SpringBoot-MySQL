package org.salem.domain.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardVO {

	private int boardNo;
	private int userNo;
	private String userName;
	private String title;
	private String content;
	private Date boardDate;
	private int hits;
	private int depth;
	private int groupNo;

	
}
