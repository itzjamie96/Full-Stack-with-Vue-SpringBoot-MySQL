package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.salem.domain.vo.SitterVO;

@Mapper
public interface SitterMapper {
	
	public List<SitterVO> showAllSitters(); //모든 시터 목록 출력
	
	public SitterVO showSitterDetail(int sitterNo); //시터 상세보기
	

	@Select("select * from sitter where sitterEmail=#{sitterEmail}") //시터 로긴 & 새로고침 관련
	public SitterVO showUserDetailLogin(@Param("sitterEmail") String sitterEmail);
	
	@Select("INSERT INTO sitter (sittingNo,sitterEmail,sitterName,sitterPw,sitterPhone,sitterAddress,sitterImg1,sitterImg2,sitterImg3,sitterImg4,sitterImg5,possibleDay,sitterGender,sitterAge,profile,petProfile,identityCheck,qualificationCheck,approvalStatus,approvalDate)" + 
			" VALUES" + 
			" (#{sittingNo},#{sitterEmail},#{sitterName},#{sitterPw},#{sitterPhone},#{sitterAddress},#{sitterImg1},#{sitterImg2},#{sitterImg3},#{sitterImg4},#{sitterImg5},#{possibleDay},1,33,'자기소개','펫소개',1,1,1,sysdate())")
	public Integer addSitter(SitterVO vo);
	
	@Update("update sitter set identityCheck=#{identityCheck},qualificationCheck=#{qualificationCheck} where sitterEmail=#{sitterEmail}")
	public void sitterCheck(SitterVO vo);
	
	
	public List<SitterVO> showDaySitters(); //하루시터 목록 출력

}
