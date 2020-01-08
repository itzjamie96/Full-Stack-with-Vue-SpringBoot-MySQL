package org.salem.domain.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.salem.domain.vo.SitterVO;

@Mapper
public interface SitterMapper {
   
   @Select("select * from sitter,sitting_type where sitter.sittingNo = sitting_type.sittingNo and approvalStatus=true")
   public List<SitterVO> showAllSitters(); //모든 시터 목록 출력
   
   @Select("select * from sitter,sitting_type where sitter.sittingNo = sitting_type.sittingNo and approvalStatus=false")
   public List<SitterVO> falseAllSitters();
   
   public SitterVO showSitterDetail(int sitterNo); //시터 상세보기
   
   @Delete("delete from sitter where sitterNo=#{sitterNo}")
   public int deleteSitter(int sitterNo);
   
   @Update("update sitter set sitterName=#{sitterName},sitterEmail=#{sitterEmail},sitterPw=#{sitterPw},sitterPhone=#{sitterPhone},sitterAddress=#{sitterAddress},approvalStatus=#{approvalStatus} where sitterNo=#{sitterNo}")
   public int updateSitter(SitterVO sitterVo);

   @Select("select * from sitter JOIN sitting_type AS type ON (sitter.sittingNo = type.sittingNo) where sitterEmail=#{sitterEmail}") //시터 로긴 & 새로고침 관련
   public SitterVO showUserDetailLogin(@Param("sitterEmail") String sitterEmail);
   
   @Insert("INSERT INTO sitter (sittingNo,sitterEmail,sitterName,sitterPw,sitterPhone,sitterAddress,sitterImg1,sitterImg2,sitterImg3,sitterImg4,sitterImg5,possibleDay,sitterGender,sitterAge,profile,petProfile,identityCheck,qualificationCheck,approvalStatus,approvalDate,lat,lng)" + 
         " VALUES" + 
         " (#{sittingNo},#{sitterEmail},#{sitterName},#{sitterPw},#{sitterPhone},#{sitterAddress},#{sitterImg1},#{sitterImg2},#{sitterImg3},#{sitterImg4},#{sitterImg5},#{possibleDay},#{sitterGender},#{sitterAge},#{profile},'펫소개',1,1,0,sysdate(),#{lat},#{lng})")
   public Integer addSitter(SitterVO vo);
   
   @Update("update sitter set identityCheck=#{identityCheck},qualificationCheck=#{qualificationCheck}, sitterImg1=#{sitterImg1}, sitterImg2=#{sitterImg2}, sitterImg3=#{sitterImg3}, sitterImg4=#{sitterImg4}, sitterImg5=#{sitterImg5} where sitterEmail=#{sitterEmail}")
   public void sitterCheck(SitterVO vo); //시터이미지 업데이트도 추가했음.
   
   
   public List<SitterVO> showDaySitters(); //하루시터 목록 출력
   
   public List<SitterVO> showHomeSitters(); //위탁시터 목록 출력
   
   public int updateSitterProfile(SitterVO sitterVO); //시터 프로필 등록,수정
   
   public List<SitterVO> showDaySitterByAddress(String area);

   public List<SitterVO> showHomeSitterByAddress(String area);
   
   public List<SitterVO> showDaySitterByDate(String date);
   
   public int updateSitterInfo(SitterVO sitterVO); //시터 정보 수정

   

}