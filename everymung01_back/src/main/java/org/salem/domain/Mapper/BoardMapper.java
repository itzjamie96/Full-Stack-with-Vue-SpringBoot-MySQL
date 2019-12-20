package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.salem.domain.vo.BoardVO;

@Mapper
public interface BoardMapper {

	@Select("select * from board order by boardNo desc")
	public List<BoardVO> showAllBoards();

	@Insert("insert into board(userNo,title,boardDate,content,depth,groupNo,hits)"
			+ "values(1,#{title},CURRENT_TIMESTAMP,#{content},1,1,7)")
	public int insertBoard(BoardVO boardVo);
	
	@Update("Update board set title=#{title},content=#{content},hits=hits+1 where boardNo=#{boardNo}")
	public int updateBoard(BoardVO boardVo);
	
	@Delete("delete from board where boardNo=#{boardNo}")
	public int deleteBoard(int boardNo);
	
}
