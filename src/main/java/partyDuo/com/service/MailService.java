package partyDuo.com.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.RandomAuth;

@Slf4j
@Service
public class MailService implements MailServiceInter{
	@Autowired
	JavaMailSender emailSender;
	
	private String ePw;

	@Override
	public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
		log.info("이메일:{}", to);
		log.info("비밀번호:{}", ePw);
		
		MimeMessage message=emailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("초기화 비밀번호 입니다.");
		
		String msgg="";
		msgg+="<h1>초기화된 비밀번호입니다.</h1>";
		msgg+="<h3>"+ePw+"</h3>";
		
		message.setText(msgg, "utf-8", "html");
		message.setFrom(new InternetAddress("gamhi777@gmail.com","paryDuo_admin"));
		return message;
	}

	@Override
	public String createKey() {
		// TODO Auto-generated method stub
		return RandomAuth.authGenerate(8);
	}

	@Override
	public String sendMessage(String to) throws Exception {
		ePw=createKey();
		log.info("ePw:{}",ePw);
		
		MimeMessage message=createMessage(to);
		
		try {
			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
		return ePw;
	}
	
	
}
