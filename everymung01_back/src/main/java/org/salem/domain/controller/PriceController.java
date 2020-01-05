package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.PriceMapper;
import org.salem.domain.vo.PriceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {
	
	@Autowired
	private PriceMapper priceMapper;
	
	@RequestMapping("/showAllPrice/{userNo}")
	public List<PriceVO> showAllPrice(@PathVariable int userNo) {
		System.out.println("showprice 실행");
		System.out.println(priceMapper.showAllPrice(userNo));
		return priceMapper.showAllPrice(userNo);
	}
	
}
