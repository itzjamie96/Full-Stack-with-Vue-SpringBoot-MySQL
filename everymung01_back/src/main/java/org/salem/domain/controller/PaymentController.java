package org.salem.domain.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.salem.domain.Mapper.PaymentMapper;
import org.salem.domain.service.KakaoPay;
import org.salem.domain.service.PaymentService;
import org.salem.domain.vo.KakaoPayApprovalVO;
import org.salem.domain.vo.PaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
	
	@Autowired
	PaymentMapper paymentMapper;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	KakaoPay kakaopay;
	
	public List<PaymentVO> list;
	
	
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
	
	@GetMapping("/kakaoPay")
	public void kakaoPayGet() {
		System.out.println("kakaoPay getMapping");
	}
	
	@PostMapping("/kakaoPay")
	public String kakaoPay(@RequestBody PaymentVO paymentVO) {
		System.out.println("kakaoPay Post");
		System.out.println("PaymentVO : " + paymentVO);
		list = new ArrayList<PaymentVO>();
		list = (List<PaymentVO>) paymentVO;
		
		int sum = paymentVO.getAmount();
		
		System.out.println(kakaopay.kakaoPayReady(list, sum));
		
		return kakaopay.kakaoPayReady(list, sum);
	}
	
	@GetMapping("/kakaoPaySuccesss")
	public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, HttpServletResponse response) throws IOException{
		
		int sum = list.get(0).getAmount();
		
		System.out.println("----kakaoPaySuccess get----");
		System.out.println("kakaoPaySuccess pg_token : " + pg_token);
		
		KakaoPayApprovalVO info = kakaopay.kakaoPayInfo(pg_token, sum);
//		int userId = Integer.parseInt(info.getPartner_user_id());
		
		System.out.println("approvalVO : " + info);
		response.sendRedirect("http://localhost:8080/");
		
	}
	
	
}
