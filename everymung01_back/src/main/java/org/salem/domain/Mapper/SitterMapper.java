package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.salem.domain.vo.SitterVO;

@Mapper
public interface SitterMapper {
	
	@Select("select * from sitter,sitting_type where sitter.sittingNo = sitting_type.sittingNo and approvalStatus=true ")
	public List<SitterVO> showAllSitters(); //모든 시터 목록 출력
	
	@Select("select * from sitter,sitting_type where sitter.sittingNo = sitting_type.sittingNo and approvalStatus=false")
	public List<SitterVO> falseAllSitters();
	
	public SitterVO showSitterDetail(int sitterNo); //시터 상세보기
	
	@Delete("delete from sitter where sitterNo=#{sitterNo}")
	public int deleteSitter(int sitterNo);
	
	@Update("update sitter set sitterName=#{sitterName},sitterEmail=#{sitterEmail},sitterPw=#{sitterPw},sitterPhone=#{sitterPhone},sitterAddress=#{sitterAddress},approvalStatus=#{approvalStatus} where sitterNo=#{sitterNo}")
	public int updateSitter(SitterVO sitterVo);
}
