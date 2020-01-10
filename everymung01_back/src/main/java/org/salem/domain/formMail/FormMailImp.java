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
		helper.setText("귀하에 지원에 다시 한번 감사드리며, 아쉽지만 저희와 함께 하지 못하여 죄송합니다.");
		helper.setSubject("지원해주셔서 정말 감사합니다!");
		
		sender.send(message);
		
	}

}
