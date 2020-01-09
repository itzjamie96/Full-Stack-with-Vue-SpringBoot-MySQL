package org.salem.domain.formMail;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class FormMailImp implements FormMailService {

	@Autowired
	JavaMailSender sender;
	
	@Override
	public void sendEmail(String email) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setTo(email);
		helper.setText("저희와는 같이 일할수 없습니다(도그메이트가서 일하세요^^).sorry");
		helper.setSubject("sorry");
		
		sender.send(message);
		
	}

}
