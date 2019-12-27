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

	@GetMapping("showAllBoards")
	public List<BoardVO> showAllBoards() {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		boardList = mapper.showAllBoards();
		return boardList;

	}

	@GetMapping("FailBoards")
	public List<BoardVO> FailBoards() {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		return mapper.FailBoards();
		
	}


	@PostMapping("/add")
	public void insertBoard(@RequestBody BoardVO boardVo) {
	
		mapper.insertBoard(boardVo); // 성공
		mapper.showAllBoards();

	}


	@PostMapping("/update")
	public void updateBoard(@RequestBody BoardVO boardVo) { // ??

		mapper.updateBoard(boardVo); // ??

		mapper.showAllBoards();

	}

	@GetMapping("/delete/{boardNo}") // {id}

	public void deleteBoard(@PathVariable int boardNo) {
		mapper.deleteBoard(boardNo);
	}

	// 답글달기
	@PostMapping("/reply") 
	public void replyBoard(@RequestBody BoardVO boardVo){
		System.out.println(boardVo);
		mapper.insertReply(boardVo);
	}
}



