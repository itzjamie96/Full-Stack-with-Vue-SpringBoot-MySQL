package org.salem.domain.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletResponse;

import org.salem.domain.Mapper.PaymentMapper;
import org.salem.domain.service.KakaoPay;
import org.salem.domain.service.PaymentService;
import org.salem.domain.vo.KakaoPayApprovalVO;
import org.salem.domain.vo.PaymentVO;

import org.salem.domain.vo.PetInfoVO;

import org.salem.domain.vo.TestVO;

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
	
	public PaymentVO vo;
	
	
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
		return paymentMapper.addPayment(paymentVO);
	}
	
	@RequestMapping("/showUserPayment/{userNo}")
	public List<PaymentVO> showUserPayment(@PathVariable int userNo) { 
		//유저의 예약 내역보기
		return paymentMapper.showUserPayment(userNo);
	}
	
	@RequestMapping("/showAllPayment")
	public List<PaymentVO> showAllPayment() { //유저의 예약 내역보기
		return (List<PaymentVO>)paymentMapper.showAllPayment();
	}
	
	@GetMapping("/test")
	public List<TestVO> test(){
		return paymentMapper.test1();
	}
	
	
	
	@GetMapping("/kakaoPay")
	public void kakaoPayGet() {
		System.out.println("kakaoPay getMapping");
	}
	
	@PostMapping("/kakaoPay")
	public String kakaoPay(@RequestBody PaymentVO paymentVO) {
		System.out.println("kakaoPay Post");
		System.out.println("PaymentVO : " + paymentVO);
		vo = paymentVO;
		
		int sum = vo.getAmount();
		
		return kakaopay.kakaoPayReady(paymentVO, sum);
	}
	
	@GetMapping("/kakaoPaySuccesss")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, 
							HttpServletResponse response) throws IOException{
		//kakaoPay 성공 시 payment INSERT
		
		int sum = vo.getAmount();
		
		System.out.println("----kakaoPaySuccess get----");
		System.out.println("kakaoPaySuccess pg_token : " + pg_token);
		
		KakaoPayApprovalVO info = kakaopay.kakaoPayInfo(pg_token, sum);
		
		
		//*********************payment insert*************************
		int insert = paymentMapper.addPayment(vo);
		System.out.println("insert : " + insert);
		
	
		PaymentVO paymentNoVO = paymentMapper.showPaymentNo(vo);
		int paymentNo = paymentNoVO.getPaymentNo();
		System.out.println("paymentNo : " + paymentNo);
		
		
		List<PetInfoVO> petInfoList = vo.getPetDetailList();
		petInfoList.forEach(i -> {
			int petNo = i.getPetNo();
			System.out.println(petNo);
			int reservationInsert = paymentMapper.addReservation(petNo, paymentNo);
			System.out.println(reservationInsert);
		});
		
		
		
		System.out.println("approvalVO : " + info);
//		response.sendRedirect("http://localhost:8080/");
		return "kakaopaySuccess~~";
		
	}
	
	
}