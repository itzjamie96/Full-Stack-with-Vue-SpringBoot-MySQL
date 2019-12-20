package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.salem.domain.vo.SitterVO;
import org.salem.domain.vo.UsersVO;

@Mapper
public interface SitterMapper {
	
	public List<SitterVO> showAllSitters(); //모든 시터 목록 출력
	
	public SitterVO showSitterDetail(int sitterNo); //시터 상세보기
	
	@Select("select * from sitter where sitterEmail=#{email}") //시터 로긴 & 새로고침 관련
	public SitterVO showUserDetailLogin(String email);
}
