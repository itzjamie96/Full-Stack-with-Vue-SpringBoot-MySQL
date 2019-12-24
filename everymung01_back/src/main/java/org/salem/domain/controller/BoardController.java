package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.BoardMapper;
import org.salem.domain.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
	
	@Autowired
	BoardMapper boardmapper;
	
	@RequestMapping("/boardlist")
	public List<BoardVO> showAllBoards(){
		return (List<BoardVO>) boardmapper.showAllBoards();
	}
}
