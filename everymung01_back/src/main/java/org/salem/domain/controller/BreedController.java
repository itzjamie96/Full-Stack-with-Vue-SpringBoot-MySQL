package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.BreedMapper;
import org.salem.domain.vo.BreedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BreedController {
	
	
	@Autowired
	BreedMapper breedMapper;
	
	
	@RequestMapping("/showAllBreedInfo")
	public List<BreedVO> showAllBreedInfo(){
        System.out.println("showAllBreedInfo 메소드 실행  ");		
		
		return breedMapper.showAllBreed();
		
	}

	

}
