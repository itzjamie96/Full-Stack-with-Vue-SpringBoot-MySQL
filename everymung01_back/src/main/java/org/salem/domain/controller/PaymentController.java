package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.PaymentMapper;
import org.salem.domain.service.PaymentService;
import org.salem.domain.vo.PaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
	
	@Autowired
	PaymentMapper paymentMapper;
	
	@Autowired
	PaymentService paymentService;
	
	@RequestMapping("/showDetailPayment/{paymentNo}")
	public PaymentVO showDetailPayment(@PathVariable int paymentNo) { //예약 상세보기
		return (PaymentVO) paymentService.showDetailPayment(paymentNo);
	}
	
	@PostMapping("/addPayment")
	public int addPayment(@RequestBody PaymentVO paymentVO) {
		////////////////////////////////////////////
		// pet 여러마리 insert 할 경우 여러번 insert 해야됨 -> 수정필요
		//
		System.out.println("controller_addPayment()");
		int result = paymentMapper.addPayment(paymentVO);
		System.out.println(result);
//		return paymentMapper.addPayment(paymentVO);
		return paymentMapper.addPayment(paymentVO);
	}
	
	@RequestMapping("/showUserPayment/{userNo}")
	public List<PaymentVO> showUserPayment(@PathVariable int userNo) { //유저의 예약 내역보기
		return paymentMapper.showUserPayment(userNo);
	}
	
	
}
