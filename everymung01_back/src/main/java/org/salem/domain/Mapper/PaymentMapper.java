package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.salem.domain.vo.PaymentVO;
import org.salem.domain.vo.TestVO;

@Mapper
public interface PaymentMapper {
	
	public int addPayment(PaymentVO paymentVO); //결제 
	
	public List<PaymentVO> showDetailPayment(int paymentNo); //결제 상세보기
	
	//유저의 예약내역 보기 
	public List<PaymentVO> showUserPayment(int userNo); 
	
	//reservation table INSERT 위한 예약 번호 SELECT  
	public PaymentVO showPaymentNo(PaymentVO paymentVO); 
	
	//payment INSERT 할때, reservation 도 같이 INSERT
	public int addReservation(int petNo, int paymentNo);
	
	//후기 작성 시 reviewStatus UPDATE
	public int updateReviewStatus(int paymentNo);

	public List<PaymentVO> showAllPayment(); 

	public List<TestVO> test1(); 
	
	//sitter에게 예약된 리스트 출력
	public List<PaymentVO> showSitterPayment(int sitterNo);
	
	public int updateRefundStatus(int paymentNo);
	
	public int addPaymentHome(PaymentVO paymentVO);
	
	public int updatePaymentStatus(int paymentNo);
	
	public List<PaymentVO> showPaymentCalendar(int sitterNo);
	
}
