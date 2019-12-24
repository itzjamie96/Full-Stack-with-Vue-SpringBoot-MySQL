package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.salem.domain.vo.SitterVO;

@Mapper
public interface SitterMapper {
	
	public List<SitterVO> showAllSitters(); //모든 시터 목록 출력
	
	public SitterVO showSitterDetail(int sitterNo); //시터 상세보기
	
	public List<SitterVO> showDaySitters(); //하루시터 목록 출력
	
}
