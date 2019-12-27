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
import org.springframework.web.bind.annotation.RestController;
@RestController
public class BoardController {

	@Autowired
	BoardMapper mapper;
<<<<<<< HEAD
	
	//@CrossOrigin(origins = "http://localhost:8080")
=======
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d

	// @CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("showAllBoards")
	public List<BoardVO> showAllBoards() {
<<<<<<< HEAD
		System.out.println("BoardController.showAllBoards start================");	
=======
		System.out.println("BoardController.showAllBoards start================");
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d
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
	public void insertBoardOne(@RequestBody BoardVO boardVo) {
		System.out.println("BoardController.insertBoardOne start================");
		System.out.println("=============================");
		System.out.println(boardVo.toString());
<<<<<<< HEAD
		System.out.println("=============================");		
		System.out.println("Insert 작업"+mapper.insertBoard(boardVo)); // 성공
		//groupNo에다가 boardNo값 update해줄 것! 
		//System.out.println("replyBoard.updateGroupNo==="+ mapper.updateGroupNo(boardVo));	// 실패


=======
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d
		System.out.println("=============================");
		System.out.println("Insert 작업" + mapper.insertBoard(boardVo)); // 성공
		// groupNo에다가 boardNo값 update해줄 것!
		// System.out.println("replyBoard.updateGroupNo==="+
		// mapper.updateGroupNo(boardVo)); // 실패
<<<<<<< HEAD

=======
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d

	}

	@PostMapping("/update")
	public void updateBoard(@RequestBody BoardVO boardVo) { // ??
		System.out.println("BoardController.updateBoard_Start==================");
		System.out.println("=============================");
		System.out.println(boardVo);
<<<<<<< HEAD
		System.out.println(mapper.updateBoard(boardVo));	// ?? 
		System.out.println(mapper.updateBoard(boardVo)); // ??
		System.out.println("=============================");
		System.out.println(mapper.showAllBoards());	
=======
		System.out.println(mapper.updateBoard(boardVo)); // ??
		System.out.println("=============================");
		System.out.println(mapper.showAllBoards());
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d

	}

	@GetMapping("/delete/{boardNo}") // {id}
<<<<<<< HEAD
	public void deleteBoard(@PathVariable int boardNo) { // URI의 일부를 변수로 전달할 수 있다 //String id = "id" // @PathVariable int boardNo
		System.out.println("delete()_"+boardNo+"번");
=======
	public void deleteBoard(@PathVariable int boardNo) { // URI의 일부를 변수로 전달할 수 있다 //String id = "id" // @PathVariable
															// int boardNo
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d
		System.out.println("delete()_" + boardNo + "번");
		System.out.println(mapper.deleteBoard(boardNo));
		System.out.println(mapper.showAllBoards());
	}
<<<<<<< HEAD
	
	//답글달기 

	// 답글달기
	@PostMapping("/reply") 
	public void replyBoard(@RequestBody BoardVO boardVo){
		System.out.println("BoardController.replyBoard__start================");
=======

	// 답글달기
	@PostMapping("/reply") 
	public void replyBoard1(@RequestBody BoardVO boardVo){
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d
		System.out.println("BoardController.replyBoard1__start================");
		System.out.println("replyBoard.boardVo==="+boardVo);

		System.out.println("replyBoard.insertReply==="+mapper.insertReply(boardVo));
<<<<<<< HEAD
		System.out.println("replyBoard.updateReply==="+mapper.updateReply(boardVo));

=======
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d
		System.out.println(mapper.showAllBoards()); // ### depth값 넘겨주기 위함 ###. 앞단에서 if(depth==1){RE] + 제목} 이런식으로 해주기 위해서 
	}


<<<<<<< HEAD
}
=======
}
>>>>>>> 23a2d3b63615d8f8371bbbcca1cf50168237765d
