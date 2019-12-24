package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.salem.domain.vo.BoardVO;

@Mapper
public interface BoardMapper {
	
	@Select("select * from board,users where board.userNo=users.userNo")
	public List<BoardVO> showAllBoards();
	
}
