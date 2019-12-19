package org.salem.domain.service;

import org.salem.domain.vo.PaymentVO;

public interface PaymentService {
	
	public int addPayment(PaymentVO paymentVO); //결제 (결제정보 추가) 주석
	
	public PaymentVO showDetailPayment(int paymentNo); //결제 상세보기

}
