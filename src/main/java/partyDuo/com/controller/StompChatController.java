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
	
	@MessageMapping(value = "/chat/enter")
    public void enter(ChatVO message){
		log.info("/chat/enter message");
        message.setChat_content(message.getChat_writer() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room2/" + message.getParty_id(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatVO message){
    	log.info("/chat/message :{}", message);
        template.convertAndSend("/sub/chat/room2/" + message.getParty_id(), message);
    }

}
