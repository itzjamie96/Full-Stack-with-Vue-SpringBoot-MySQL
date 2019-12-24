package org.salem.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.salem.domain.Mapper.PaymentMapper;
import org.salem.domain.vo.PaymentVO;
import org.salem.domain.vo.PetInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	PaymentMapper paymentMapper;

	@Override
	public int addPayment(PaymentVO paymentVO) {
		
		return paymentMapper.addPayment(paymentVO);
	}

	@Override
	public PaymentVO showDetailPayment(int paymentNo) {
		
		List<PetInfoVO> petList = new ArrayList<PetInfoVO>();
		List<PaymentVO> list = paymentMapper.showDetailPayment(paymentNo);
		PaymentVO paymentVO = new PaymentVO();
		paymentVO = list.get(0);
		
		list.forEach(i -> {
				PetInfoVO petInfoVO = new PetInfoVO();
				petInfoVO.setPetName(i.getPetName()); 
				petInfoVO.setPetNo(i.getPetNo());
				petInfoVO.setSize(i.getSize());
				petList.add(petInfoVO);
		});
		
		paymentVO.setPetDetailList(petList);
		
		return paymentVO;
	}

}
