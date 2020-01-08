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
	
	//Create
	@Insert("insert into board(userNo,title,content,boardDate,groupNo,depth,hits)"
			+ "values(#{userNo},#{title},#{content},CURRENT_TIMESTAMP, get_seq('groupNoSeq'),0,0)")
	public int insertBoard(BoardVO boardVo);
	
	//Read_답글형 정렬+users테이블 JOIN을 적용한 전체조회  (BOARD테이블에서 USER테이블의 userName컬럼을 갖다쓰기 위해서 JOIN했음)  
	@Select("SELECT * FROM board LEFT JOIN users ON board.userNo=users.userNo ORDER BY groupNo DESC, depth ASC")
	public List<BoardVO> showAllBoards();
		
	//Update
	@Update("Update board set title=#{title},content=#{content} where boardNo=#{boardNo}")
	public int updateBoard(BoardVO boardVo);
	
	//유저_Delete ( 유저페이지에서 삭제버튼 클릭시 수행되는 쿼리문)
	@Delete("delete from board where groupNo=#{groupNo}")
	public int deleteBoardByUser(int groupNo);
	
	//조회수_Select_메커니즘_기존 클릭된 조회수 총합(0<=n) + 현재 나의 클릭(+1)
	@Select("select hits + 1 as AddHits from board where boardNo = #{boardNo}")
	public int selectOriHits(BoardVO boardVo);
	
	//조회수_Update
	@Update("update board set hits = #{hits} where boardNo = #{boardNo}")
	public void updateHits(BoardVO boardVo);	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//관리자_답글_Create(userNo에 관리자번호를 집어넣어야되는데, 관리자번호가 변경될 수 있기 때문에, 최종점검 때 해당값을 점검해줘야함)
	@Insert("INSERT INTO board(userNo,title, content, boardDate, groupNo, depth, hits)"
			+ "VALUES( 0, #{title}, #{content}, CURRENT_TIMESTAMP, #{groupNo}, 1, 0)") // depth에 '1'로 하드코딩함. 
	public int insertReply(BoardVO boardVo);
	
	//관리자_답글_수정
	@Update("update board set content=#{content} where boardNo=#{boardNo}")
	public int updateBoardByMngr(BoardVO boardVo);

	//관리자_전체조회_Read
	@Select("select * from board,users where board.userNo=users.userNo order by status ASC,groupNo DESC, depth ASC, boardDate DESC")
	public List<BoardVO> showAdminBoards();
	
	//관리자_원글_Update_status를 true로 수정 
	@Update("Update board set status=#{status} where groupNo=#{groupNo}")
	public int updateStatus(BoardVO boardVo);
	
	//관리자_유저게시글_Delete (관리자페이지에서 삭제버튼 클릭시 수행되는 쿼리문)
	@Delete("delete from board where GroupNo=#{groupNo}")
	public int deleteBoardByMngr(int groupNo);
	
	//관리자_댓글_Delete
	@Delete("delete from board where boardNo=#{boardNo}")
	public int deleteReply(int boardNo);
	
	//관리자_댓글_count
	@Select("select * from board where status=false")
	public List<BoardVO> falseBoard();
	
	
		
}
