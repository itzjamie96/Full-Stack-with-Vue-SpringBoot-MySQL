package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.PaymentMapper;
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
	
	@Autowired
	PaymentMapper paymentMapper;
	
	@RequestMapping("/showSitterReview/{sitterNo}")
	public List<ReviewVO> showSitterReview(@PathVariable int sitterNo){ //시터의 리뷰 출력
		return (List<ReviewVO>) reviewMapper.showSitterReview(sitterNo);
	}
	
	@PostMapping("/addReview")
	public int addReview(@RequestBody ReviewVO reviewVO) {
		//후기 작성 시 payment table의 reviewStatus도 같이 update
		System.out.println("addReview");
		paymentMapper.updateReviewStatus(reviewVO.getPaymentNo());
		return reviewMapper.addReview(reviewVO);
	}
	
	//메인에 띄울 시터 후기
	@RequestMapping("/showFeaturedReview")
	public List<ReviewVO> showFeaturedReview(){ //시터의 리뷰 출력
		System.out.println("reviews");
		return (List<ReviewVO>) reviewMapper.showFeaturedReview();
	}
}