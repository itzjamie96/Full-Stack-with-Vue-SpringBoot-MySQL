package org.salem.domain.controller;

import java.util.List;
import org.salem.domain.Mapper.SitterMapper;
import org.salem.domain.vo.SitterVO;
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

	@RequestMapping("/falseAllSitters")
	public List<SitterVO> falseAllSitters() {
		return (List<SitterVO>) sitterMapper.falseAllSitters();
	}
	
	@PostMapping("/deleteSitter/{sitterNo}")
	public int deleteSitter(@PathVariable int sitterNo) {
		return sitterMapper.deleteSitter(sitterNo);
		
	}
	
	@PostMapping("/updateSitter")
	public int updateSitter(@RequestBody SitterVO sitterVo) {
		System.out.println("controller:"+sitterVo);
		return sitterMapper.updateSitter(sitterVo);
	}
	
//	@RequestMapping("/showSitterDetail/{sitterNo}")
//	public SitterVO showSitterDetail(@PathVariable int sitterNo) {
//		return (SitterVO) sitterMapper.showSitterDetail(sitterNo);
//	}

}
