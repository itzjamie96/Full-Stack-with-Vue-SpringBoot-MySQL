package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.ReviewMapper;
import org.salem.domain.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewContoller {
	
	@Autowired
	ReviewMapper reviewMapper;
	
	@RequestMapping("/showSitterReview/{sitterNo}")
	public List<ReviewVO> showSitterReview(@PathVariable int sitterNo){ //시터의 리뷰 출력
		return (List<ReviewVO>) reviewMapper.showSitterReview(sitterNo);
	}
	
	@PostMapping("/addReview")
	public int addReview(@RequestBody ReviewVO reviewVO) {
		System.out.println("addReview");
		//paymentVO update 추가해야함
		return reviewMapper.addReview(reviewVO);
	}
}
