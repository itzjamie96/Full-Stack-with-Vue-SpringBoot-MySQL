package org.salem.domain.controller;

import java.util.ArrayList;
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
	
	//@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("showAllBoards")
	public List<BoardVO> showAllBoards(){
		System.out.println("BoardController.showAllBoards start================");
		List<BoardVO> boardList = new ArrayList<BoardVO>();		
		boardList = mapper.showAllBoards();
//		System.out.println("=============================");
//		for (BoardVO bVO : boardList) {			
//			System.out.println(bVO.toString()); 			
//		}		
//		System.out.println("=============================");
		return boardList;

	}
	@PostMapping("/add") 
	public void insertBoardOne(@RequestBody BoardVO boardVo){
		System.out.println("BoardController.insertBoardOne start================");
		System.out.println("=============================");
		System.out.println(boardVo.toString());
		System.out.println("=============================");		
		System.out.println("Insert 작업"+mapper.insertBoard(boardVo)); // 성공
		//groupNo에다가 boardNo값 update해줄 것! 
		System.out.println("replyBoard.updateGroupNo==="+ mapper.updateGroupNo(boardVo));	// 실패
		


		
	}
	@PostMapping("/update") 
	public void updateBoard(@RequestBody BoardVO boardVo){	// ?? 
		System.out.println("BoardController.updateBoard_Start==================");
		System.out.println("=============================");
		System.out.println(boardVo);
		System.out.println(mapper.updateBoard(boardVo));	// ?? 
		System.out.println("=============================");
		System.out.println(mapper.showAllBoards());
		
	}
	@GetMapping("/delete/{boardNo}") // {id}
	public void deleteBoard(@PathVariable int boardNo) { // URI의 일부를 변수로 전달할 수 있다 //String id = "id" // @PathVariable int boardNo
		System.out.println("delete()_"+boardNo+"번");
		System.out.println(mapper.deleteBoard(boardNo));
		System.out.println(mapper.showAllBoards());
	}
	
	//답글달기 
	@PostMapping("/reply") 
	public void replyBoard(@RequestBody BoardVO boardVo){
		System.out.println("BoardController.replyBoard__start================");
		System.out.println("replyBoard.boardVo==="+boardVo);
		
		System.out.println("replyBoard.insertReply==="+mapper.insertReply(boardVo));
		System.out.println("replyBoard.updateReply==="+mapper.updateReply(boardVo));
		
	}
}
