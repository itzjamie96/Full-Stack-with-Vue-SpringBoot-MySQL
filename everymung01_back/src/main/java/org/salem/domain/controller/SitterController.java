package org.salem.domain.controller;

import java.util.List;
import org.salem.domain.Mapper.SitterMapper;
import org.salem.domain.vo.SitterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping("/showSitterDetail/{sitterNo}")
	public SitterVO showSitterDetail(@PathVariable int sitterNo) {
		return (SitterVO) sitterMapper.showSitterDetail(sitterNo);
	}

}
