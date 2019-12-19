package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.salem.domain.vo.PaymentVO;

@Mapper
public interface PaymentMapper {
	
	public int addPayment(PaymentVO paymentVO); //결제 (결제정보 추가)
	
	public List<PaymentVO> showDetailPayment(int paymentNo); //결제 상세보기
}
