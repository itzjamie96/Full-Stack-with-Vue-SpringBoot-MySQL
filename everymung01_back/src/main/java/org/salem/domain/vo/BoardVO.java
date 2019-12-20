package org.salem.domain.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BoardVO {

	private int boardNo;
	private int userNo;
	private String title;
	private String content;
	private String boardDate;
	private int hits;
	private int depth;
	private int groupNo;
	public BoardVO(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
}
