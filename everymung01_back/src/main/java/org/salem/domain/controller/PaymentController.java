package org.salem.domain.controller;

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
	public PaymentVO showDetailPayment(@PathVariable int paymentNo) {
		return (PaymentVO) paymentService.showDetailPayment(paymentNo);
	}
	
	@PostMapping("/addPayment")
	public int addPayment(@RequestBody PaymentVO paymentVO) {
		System.out.println("cont_addPayment()");
		int result = paymentMapper.addPayment(paymentVO);
		System.out.println(result);
		return paymentMapper.addPayment(paymentVO);
	}
	
	
}
