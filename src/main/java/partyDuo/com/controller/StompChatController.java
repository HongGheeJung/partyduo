package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.ChatVO;
import partyDuo.com.service.ChatService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	private ChatService chatService;
	
	@MessageMapping(value = "/chat/message")
    public void message(ChatVO message) {
        System.out.println("message connection");
        
        chatService.insertRoomDatas(message);
        template.convertAndSend("/sub/chat/room/" + message.getParty_id(), message);
    }
	
	
	

}
