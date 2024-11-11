package partyDuo.com.controller;


import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.ChatService;
import partyDuo.com.service.PartyService;
import partyDuo.com.model.ChatVO;
import partyDuo.com.model.PartyVO;

@Slf4j
@Controller
public class ChatController {
	
	@Autowired
	ChatService service;
	
	@Autowired
	PartyService pservice;
	
	@PostMapping("/chat/insertOK")
	public String insertOK(ChatVO vo) throws IllegalStateException, IOException {
		log.info("/chat/insertOK");


		int result = service.insertOK(vo);
		log.info("result:{}", result);

			return "redirect:/event/calendar";
	}

	@PostMapping("/chat/deleteOK")
	public String deleteOK(ChatVO vo, Model model) {
		log.info("/chat/deleteOK");

		vo = service.selectOne(vo);

		int party_id = vo.getParty_id();
		int result = service.deleteOK(vo);
		log.info("result:{}", result);
				
		PartyVO room = (pservice.searchList("party_id", Integer.toString(party_id))).get(0);
        
        List<ChatVO> list = service.searchListParty(party_id);


		model.addAttribute("chat_list", list);   
        model.addAttribute("room", room);

        return "room";
			
	}
	
	@GetMapping("/chat/room2/{id}")
    public String room(@PathVariable String id,
    		Model model) {

        PartyVO room = (pservice.searchList("party_id", id)).get(0);
        int party_id = Integer.parseInt(id);
        List<ChatVO> list = service.searchListParty(party_id);


		model.addAttribute("chat_list", list);   
        model.addAttribute("room", room);

        return "room";
    }
	
	

}
