package org.salem.domain.controller;

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


	
	//@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("showAllBoards")
	public List<BoardVO> showAllBoards(){
		System.out.println(mapper.showAllBoards());
		return mapper.showAllBoards();
//		return (List<Users>) rep.findAll();
	}
	@PostMapping("/add") 
	public void addException(@RequestBody BoardVO boardVo){
		System.out.println("-----");
		System.out.println(boardVo);
		System.out.println(mapper.insertBoard(boardVo));
		System.out.println(mapper.showAllBoards());
		
	}
	@PostMapping("/update") 
	public void updateException(@RequestBody BoardVO boardVo){	// ?? 
		System.out.println(boardVo);
		System.out.println(mapper.updateBoard(boardVo));	// ?? 
		System.out.println(mapper.showAllBoards());
		
	}
	@GetMapping("/delete/{boardNo}") // {id}
	public void deleteLsm(@PathVariable int boardNo) { // URI의 일부를 변수로 전달할 수 있다 //String id = "id" // @PathVariable int boardNo
		System.out.println("delete()_"+boardNo+"번");
		System.out.println(mapper.deleteBoard(boardNo));
		System.out.println(mapper.showAllBoards());
	}


}
