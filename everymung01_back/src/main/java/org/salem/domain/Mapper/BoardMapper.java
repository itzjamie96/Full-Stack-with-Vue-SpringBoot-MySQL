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
	
	//답글형으로 전체조회  (BOARD테이블에서 USER테이블의 userName컬럼을 갖다쓰기 위해서 JOIN + 답글형 조회 )  
	@Select("SELECT * FROM board LEFT JOIN users ON board.userNo=users.userNo ORDER BY groupNo DESC, depth ASC")
	public List<BoardVO> showAllBoards();

	//원글_Insert
	@Insert("insert into board(userNo,title,content,boardDate,groupNo,depth,hits)"
			+ "values(#{userNo},#{title},#{content},CURRENT_TIMESTAMP, get_seq('groupNoSeq'),0,0)")
	public int insertBoard(BoardVO boardVo);

//	//1.원글_Update => 답글 달려는 원글의 groupNo값을 식별값으로 바꿔주는 작업. 


	//2.답글_Insert => 답글을 insert하는 작업으로, groupNo값에 #{boardNo}을 넣어줘서, 원글과 답글의 groupNo값을 갖게 해주기. 
	@Insert("INSERT INTO board(userNo,title, content, boardDate, groupNo, depth, hits)"
			+ "VALUES( #{userNo}, #{title}, #{content}, CURRENT_TIMESTAMP, #{groupNo}, 1, 0)") // depth에 '1'로 하드코딩함. 
	public int insertReply(BoardVO boardVo);
		// VALUES()에 #{groupNo}를 넣어놨던데, #{baordNo}를 넣어야할지 말지 매우 헷갈림 

	//3.답글_Update => insert된 답글의 depth를 1증가시켜서 -> 답글형으로 정렬되도록 세팅  
	@Update("UPDATE board SET depth=depth+1 WHERE groupNo=#{boardNo}")
	public int updateReply(BoardVO boardVo);
		// WHERE groupNo=#{groupNo} ===> 이해못함 
//	//3.답글_Update => insert된 답글의 depth를 1증가시켜서 -> 답글형으로 정렬되도록 세팅  
//	//update_계속 실패중 
//	@Update("UPDATE board SET depth=depth+1 WHERE groupNo=#{groupNo}") //### WHERE groupNo=#{groupNo}  ===> 여기서 막힘.
//	public int updateReply(BoardVO boardVo);
//		// WHERE groupNo=#{groupNo} ===> 이해못함 

	//수정하기
	@Update("Update board set title=#{title},content=#{content} where boardNo=#{boardNo}")
	public int updateBoard(BoardVO boardVo);
	
	//삭제하기
	@Delete("delete from board where boardNo=#{boardNo}")
	public int deleteBoard(int boardNo);
	
	
/*
			<<답글_인서트_가데이터>>
(매개변수ver)
INSERT INTO board(userNo,title, content, boardDate, hits, groupNo, depth)
	VALUES( 1, #{title}, #{content}, CURRENT_TIMESTAMP, 0,  #{groupNo}, #{depth})
(하드코딩ver)
INSERT INTO board(userNo,title, content, boardDate, hits, groupNo, depth)
	VALUES( 1, 'ㅁㄴㅇㄹㄹ_답글 테스트', 'ㅁㅇㄴㅁ_답글 테스트', CURRENT_TIMESTAMP, 0,  6, 0)  '답글 테스트', '답글 테스트'  / 're_답글 테스트', 're_답글 테스트'
			<<답글_업데이트_가데이터>>
(매개변수ver)
UPDATE board SET depth=depth+1 WHERE groupNo=#{groupNo}
(하드코딩ver)
UPDATE board SET depth=depth+1 WHERE groupNo=6
			<<답글_셀렉트_답글형으로 정렬해서 나타내기>>
SELECT * FROM board ORDER BY groupNo DESC, depth ASC
 * 
 */
	
	
	/*
	 * 	
	 * 				<매퍼 인터페이스 메소드 선언부>
	int insertQ(Board1 board);
	int insertA(int boardNo, int userNo, String title, String userName, String content, int boardDate, int hits,  int originNo, int groupOrder, int groupLayer);
	int updateGrouplayer(int originno, int groupord);
	
					<매퍼 xml_SQL문>
	
	<insert id="insertQ">
	INSERT INTO boardList1(userNo,title, userName, content, boardDate, hits, originNo, groupOrder, groupLayer) 
	VALUES( 1,#{title}, #{content}, CURRENT_TIMESTAMP, 0,  1, 0)
	</insert>
	
	
	<insert id="insertA">
	INSERT INTO boardList1(userid, boardtitle, boardno, boardpw, contentp, regdate, originno, groupord,  grouplayer, countp) 
	VALUES(#{userid}, #{boardtitle}, seq_board1.NEXTVAL, #{boardpw}, #{contentp}, sysdate, #{originno}, #{groupord}, #{grouplayer}, 0)
	</insert>
	
	<update id="updateGrouplayer">
		UPDATE boardList1 SET groupord=groupord+1 WHERE originno=#{originno} AND groupord>#{groupord}
	</update>
	
	
	*/
}