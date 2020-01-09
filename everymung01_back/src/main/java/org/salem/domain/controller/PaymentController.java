package org.salem.domain.controller;

import java.io.IOException;
import java.util.List;

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

		
		vo.setTid(info.getTid());
		//*********************payment insert*************************
		System.out.println("**vo** : " + vo);
		int insert = 0;
		if(vo.getSittingType().equals("daycare")) {
			insert = paymentMapper.addPayment(vo);
		}else {
			insert = paymentMapper.addPaymentHome(vo);
		}
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
		return "kakaopaySuccess~~";
		
	}
	
	@RequestMapping("/showSitterPayment/{sitterNo}")
	public List<PaymentVO> showSitterPayment(@PathVariable int sitterNo){ //시터예약리스트
		return paymentMapper.showSitterPayment(sitterNo);
	}
	
	@PostMapping("/refund")
	public String refund(@RequestBody PaymentVO paymentVO) {
		System.out.println("환불처리------"); 
		System.out.println("tid --> " + paymentVO.getTid());
		kakaopay.kakaoCancel(Integer.toString(paymentVO.getAmount()), paymentVO.getTid());
		paymentMapper.updateRefundStatus(paymentVO.getPaymentNo());
		return "refund";
	}
	
	@RequestMapping("/updatePaymentStatus/{paymentNo}")
	public int updatePaymentStatus(@PathVariable int paymentNo) {
		System.out.println("payment update");
		return paymentMapper.updatePaymentStatus(paymentNo);
	}
	
	@RequestMapping("/showPaymentCalendar/{sitterNo}")
	public List<PaymentVO> showPaymentCalendar(@PathVariable int sitterNo){ //시터예약리스트-캘린더
		return paymentMapper.showPaymentCalendar(sitterNo);
	}
	
	
	
}