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
	
	//userName 갖다쓰기 위해서 JOIN후 전체조회 
	@Select("SELECT * FROM board LEFT JOIN users ON board.userNo=users.userNo ORDER BY groupNo DESC, depth ASC")
	public List<BoardVO> showAllBoards();

	//	@Select("SELECT * FROM board ORDER BY groupNo DESC, depth ASC")
	//	public List<BoardVO> showReplyBoards();


	@Insert("insert into board(userNo,title,boardDate,content,depth,groupNo,hits)"
			+ "values(#{userNo},#{title},CURRENT_TIMESTAMP,#{content},1,1,7)")
	// userName도 입력을 해줘야되나? 이미 입력된 값이니까 난 가져오기만 하면 되는 거 아닌가? 
	// userNo를 1로 하드코딩했다가, #{userNo}로 넘겨받는 값으로 바꾸니까, insert가 안됨. 
	// #{userNo}값을 넘겨받으려면 어떻게 해야되지? => 도희 "vuex에서 가져올 것?"
	public int insertBoard(BoardVO boardVo);
	
	@Update("Update board set title=#{title},content=#{content},hits=hits+1 where boardNo=#{boardNo}")
	public int updateBoard(BoardVO boardVo);
	
	@Delete("delete from board where boardNo=#{boardNo}")
	public int deleteBoard(int boardNo);
	
	//답글달기_Insert 
	@Insert("INSERT INTO board(userNo,title, content, boardDate, hits, groupNo, depth)"
			+ "VALUES( 1, #{title}, #{content}, CURRENT_TIMESTAMP, 0,  #{groupNo}, #{depth})")
	public int insertReply(BoardVO boardVo);
		// userNo를 #{userNo}로 바꾸면 에러가 심하게 났기 때문에, 일단은 하드코딩. 
	
	//답글달기_Update
	@Update("UPDATE board SET depth=depth+1 WHERE groupNo=#{groupNo}")
	public int updateReply(int groupNo);
	
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
	 * <매퍼 인터페이스 메소드 선언부>
	int insertQ(Board1 board);
	int insertA(int boardNo, int userNo, String title, String userName, String content, int boardDate, int hits,  int originNo, int groupOrder, int groupLayer);
	int updateGrouplayer(int originno, int groupord);
	
	<매퍼 xml_SQL문>
	<insert id="insertQ">
	INSERT INTO boardList1(userNo,title, userName, content, boardDate, hits, originNo, groupOrder, groupLayer) 
	VALUES( 1,#{title}, #{content}, CURRENT_TIMESTAMP, 0,  1, 0)
	</insert>
	
	//userNo도 auto인크리먼트인가? 안써줘도 되나? #{}는 아니겠지?  
	
	<insert id="insertA">
	INSERT INTO boardList1(userid, boardtitle, boardno, boardpw, contentp, regdate, originno, groupord,  grouplayer, countp) 
	VALUES(#{userid}, #{boardtitle}, seq_board1.NEXTVAL, #{boardpw}, #{contentp}, sysdate, #{originno}, #{groupord}, #{grouplayer}, 0)
	</insert>
	
	<update id="updateGrouplayer">
		UPDATE boardList1 SET groupord=groupord+1 WHERE originno=#{originno} AND groupord>#{groupord}
	</update>
	
	
	*/
}
