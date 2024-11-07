package partyDuo.com.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public interface MailServiceInter {
	
	String createKey();
	
	String sendMessage(String to, String title, String content) throws Exception;

	MimeMessage createMessage(String to, String title, String content)
			throws MessagingException, UnsupportedEncodingException;
}
