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
	public MimeMessage createMessage(String to, String title, String content) throws MessagingException, UnsupportedEncodingException {
		log.info("이메일:{}", to);
		log.info("제목:{}", title);
		log.info("내용:{}", content);
		
		MimeMessage message=emailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject(title);
		
		String msgg=content;
		
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
	public String sendMessage(String to, String title, String content) throws Exception {
		
		MimeMessage message=createMessage(to, title, content);
		
		try {
			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
		return content;
	}
	
}
