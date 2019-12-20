package org.salem.domain.controller;

import java.util.List;
import org.salem.domain.Mapper.SitterMapper;
import org.salem.domain.vo.LoginVO;
import org.salem.domain.vo.SitterVO;
import org.salem.domain.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SitterController {
	
	//testing
	
	@Autowired
	SitterMapper sitterMapper;
	
	@RequestMapping("/showAllSitters")
	public List<SitterVO> showAllSitters() {
		return (List<SitterVO>) sitterMapper.showAllSitters();
	}
	
	@PostMapping("refreshSitter")//시터 새로고침
	public SitterVO refreshE(@RequestBody LoginVO vo) {
		
		System.out.println(vo);
		return sitterMapper.showUserDetailLogin(vo.getEmail());
		
		
	}
	
	@PostMapping("signinSitter") //시터로그인 로그인
	public SitterVO signin(@RequestBody LoginVO vo) {
		System.out.println(vo);
		SitterVO sitter =sitterMapper.showUserDetailLogin(vo.getEmail());
		System.out.println(sitter);
		if(sitter.getSitterPw().equals(vo.getPassword()))
		{
			System.out.println("성공");
			return sitter;
		}
		else 
		{System.out.println("실패~");
		return new SitterVO();
		}
		
	}
	
	@RequestMapping("/showSitterDetail/{sitterNo}")
	public SitterVO showSitterDetail(@PathVariable int sitterNo) {
		return (SitterVO) sitterMapper.showSitterDetail(sitterNo);
	}

}
