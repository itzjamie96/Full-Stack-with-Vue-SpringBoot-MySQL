package org.salem.domain.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.salem.domain.Mapper.BoardMapper;
import org.salem.domain.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
	
	@Autowired
	BoardMapper mapper;

	// @CrossOrigin(origins = "http://localhost:8080")
	//전체조회
	@GetMapping("showAllBoards")
	public List<BoardVO> showAllBoards() {

		List<BoardVO> boardList = new ArrayList<BoardVO>();
		boardList = mapper.showAllBoards();
		
		return boardList;
	}

	
	//글쓰기
	@PostMapping("/add")
	public int insertBoard(@RequestBody BoardVO boardVo) {
		System.out.println("BoardController.insertBoard_start================");
		System.out.println("=============================");
		System.out.println(boardVo.toString());
		System.out.println("=============================");
		return mapper.insertBoard(boardVo);
	}
	
	//수정
	@PostMapping("/update")
	public int updateBoard(@RequestBody BoardVO boardVo) { // ??
		System.out.println("BoardController.updateBoard_Start==================");
		System.out.println("=============================");
		System.out.println(boardVo);
		System.out.println(mapper.updateBoard(boardVo));
		System.out.println("=============================");
		return mapper.updateBoard(boardVo);
	}

	
	//유저페이지 삭제버튼
	@GetMapping("/deleteBoardByUser/{groupNo}") // {id}
	public int deleteBoardByUser(@PathVariable int groupNo) { // URI의 일부를 변수로 전달할 수 있다 //String id = "id" // @PathVariable
															// int groupNo
		System.out.println("delete()_groupNo=" + groupNo + "번");
		//원글 삭제시 답글까지 같이 삭제되게 하는 로직짜보기 => 파라미터를 boardNo -> groupNo로 바꿔주고, 재조회하면 해결될 것 같음.
	
		return mapper.deleteBoardByUser(groupNo);
	}
	
	//관리자페이지 삭제버튼
	@PostMapping("/deleteBoardByMngr") // {id}
	public void deleteBoardByMngr(@RequestBody BoardVO boardVo) { // URI의 일부를 변수로 전달할 수 있다 //String id = "id" // @PathVariable
		System.out.println(boardVo);
		if(boardVo.getUserNo() == 0) {
			 mapper.deleteReply(boardVo.getBoardNo());
			 mapper.updateStatus(boardVo);
		}else
			 mapper.deleteBoardByMngr(boardVo.getGroupNo());
	}
	
	//관리자페이지 답글수정
	@PostMapping("/updateBoardByMngr")
	public int updateBoardByMngr(@RequestBody BoardVO boardVo) {
		return mapper.updateBoardByMngr(boardVo);

	}

	//관리자_답글달기
	@PostMapping("/insertReply") 
	public void replyBoard(@RequestBody BoardVO boardVo){ 
			//반환타입을 void로 하면 sysout("=======")같은 걸로 맨끝줄에 위치시켜서 감싸주는 게 가능한데, 
			//반환타입이 있는 경우는 return이 맨아랫줄에 위치해야하기 때문에, 맨아랫줄에 sysout을 사용할 수가 없어서 살짝 불편한 것 같다. 
		System.out.println("BoardController.replyBoard__start================");
		System.out.println("BoardController.replyBoard.boardVo::"+boardVo);
		mapper.insertReply(boardVo);
		mapper.updateStatus(boardVo); // 01.03_15:23분_추가 
		System.out.println("BoardController.replyBoard__End================");
		//return mapper.showOneBoard(boardVo.getBoardNo()); //앞딴에서 DB데이터가 담긴 BoardVO객체 하나를 받기위해 리턴해줌! 
	}

	//관리자 페이지_조회 
	@GetMapping("/showAdminBoards")
	public List<BoardVO> showAdminBoards(){ // 파라미터에 boolean status 넣었다가, 민우 말대로 제거해봄. 
		
		return mapper.showAdminBoards();
	}
	
	//관리자 페이지_미답변 count
	@RequestMapping("/falseBoard")
	public List<BoardVO> falseBoard() {
		return mapper.falseBoard();
	}
		
	
	//조회수 처리
	@PostMapping("/updateHits")
	public int updateHits(@RequestBody BoardVO boardVo) {		
		System.out.println("BoardController_updateHits()_boardNo값 확인하기"+boardVo.getBoardNo());	
		int addHits;
		addHits = mapper.selectOriHits(boardVo);
		boardVo.setHits(addHits);		
		mapper.updateHits(boardVo);
		return addHits;
	}
}



