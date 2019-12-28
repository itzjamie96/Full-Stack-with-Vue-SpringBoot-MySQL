package org.salem.domain.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.salem.domain.vo.KakaoPayApprovalVO;
import org.salem.domain.vo.KakaoPayReadyVO;
import org.salem.domain.vo.PaymentVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;

@Log
@Service
public class KakaoPay {
	
	private static final String HOST = "https://kapi.kakao.com";
	
	private KakaoPayReadyVO kakaoPayReadyVO;
	private KakaoPayApprovalVO kakaoPayApprovalVO;
	
	public List<PaymentVO> list = new ArrayList();
	
	public int sum=0;
	
	public String kakaoPayReady(PaymentVO paymentVO, int sum) {
		//**************가격이랑 상품명 수정 예정**************
		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println("kakaoPayReady---");
		String userId = Integer.toString(paymentVO.getPaymentNo());   //수정
		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "189cb444d1d0a80af1fb1bcaef868817");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		System.out.println(headers);
		String s = Integer.toString(sum);
		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", "1001");  //가맹점 주문 번호
		params.add("partner_user_id", "1111");  //가맹점 회원 id
		params.add("item_name", "상품명");  //상품명
		params.add("quantity", "1");
		params.add("total_amount", "5555");
		params.add("tax_free_amount", "100");
		params.add("approval_url", "http://localhost:8080/kakaoPaySuccess");
		params.add("cancel_url", "http://localhost:8080/kakaoPayCancel");
		params.add("fail_url", "http://localhost:8080/kakaoPaySuccessFail");
		System.out.println(params);
		System.out.println("=======");
	
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body,
					KakaoPayReadyVO.class);

			return kakaoPayReadyVO.getNext_redirect_pc_url();

		} catch (RestClientException e) {

			e.printStackTrace();
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

		return "/pay";  //에러 시 넘어가는 페이지

	}

	public KakaoPayApprovalVO kakaoPayInfo(String pg_token,int sum) {  //반환값이 주소값


		RestTemplate restTemplate = new RestTemplate();
		String s = Integer.toString(sum);
//		String userId = Integer.toString(paymentVO.getPaymentNo());   //수정
		
		// 서버로 보낼 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "8921a420fe7e75c1306abfe0fc7e5b52");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 보낼 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("tid", kakaoPayReadyVO.getTid());
		params.add("partner_order_id", "1001");
		params.add("partner_user_id", "1111");
		params.add("pg_token", pg_token);
		params.add("total_amount", "5555");
		
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body,
					KakaoPayApprovalVO.class);

			return kakaoPayApprovalVO;

		} catch (RestClientException e) {

			e.printStackTrace();
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

		return null;
	}
	
}
