package org.salem.domain.service;

import java.net.URI;
import java.net.URISyntaxException;

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

   public PaymentVO vo;

   
   public int sum=0;
   
   public String kakaoPayReady(PaymentVO paymentVO, int sum) {
      RestTemplate restTemplate = new RestTemplate();
      vo = paymentVO;
      System.out.println("kakaoPayReady---");
      // 서버로 요청할 Header
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "KakaoAK " + "");
      headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
      headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
      System.out.println(headers);
      String s = Integer.toString(sum);
      // 서버로 요청할 Body
      MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
      params.add("cid", "TC0ONETIME");
      params.add("partner_order_id", "1111");  //가맹점 주문 번호
      params.add("partner_user_id", "1111");  //가맹점 회원 id
      params.add("item_name", "에브리멍");  //상품명
      params.add("quantity", "1");
      params.add("total_amount", s);
      
      params.add("tax_free_amount", "0");
      params.add("vat_amount", "0");
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
//      String userId = Integer.toString(paymentVO.getPaymentNo());   //수정
      String paymentNo = Integer.toString(vo.getPaymentNo()); 
      
      // 서버로 보낼 Header
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "KakaoAK " + "");
      headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
      headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

      // 서버로 보낼 Body
      MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
      params.add("cid", "TC0ONETIME");
      params.add("tid", kakaoPayReadyVO.getTid());
      params.add("partner_order_id", "1111");
      params.add("partner_user_id", "1111");
      params.add("vat_amount", "0");
      params.add("tax_free_amount", "0");
      
      params.add("pg_token", pg_token);
      params.add("total_amount", s);
      
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
   
   public void kakaoCancel(String cancelAmount, String tid){
      System.out.println(cancelAmount + "  : "+tid);
      RestTemplate restTemplate = new RestTemplate();
      // 서버로 요청할 Header
      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "KakaoAK " + "");
      headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
      headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
      System.out.println("환불 header  :   " + headers);
      // 서버로 요청할 Body
      MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
      params.add("cid", "TC0ONETIME");
      params.add("tid", tid);
      params.add("cancel_available_amount", cancelAmount);
      params.add("cancel_amount", cancelAmount );
      params.add("cancel_tax_free_amount", "0");
      params.add("cancel_vat_amount", "0");
      System.out.println("환불 params  :  " + params);
      HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
      System.out.println(body);

      try {
         System.out.println("try-----------");
            System.out.println(restTemplate.postForObject(new URI(HOST + "/v1/payment/cancel"), body,
            KakaoPayApprovalVO.class));
         
         // log.info("" + kakaoPayApprovalVO);

      } catch (RestClientException e) {
         e.printStackTrace();
      } catch (URISyntaxException e) {
         e.printStackTrace();
      }
      
   }
   
   
   
}