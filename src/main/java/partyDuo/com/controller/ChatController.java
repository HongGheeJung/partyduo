package partyDuo.com.controller;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
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
	
	@GetMapping("/chat/insert")
	public String insert() {
		log.info("/chat/insert");
		return "chat/insert";
	}
	
	@GetMapping("/chat/update")
	public String update(ChatVO vo, Model model) {
		log.info("/chat/update");		
		log.info("vo:{}", vo);

		ChatVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "chat/update";
	}

	@GetMapping("/chat/delete")
	public String delete() {
		log.info("/chat/delete");
		return "chat/delete";
	}

	@GetMapping("/chat/selectAll")
	public String selectAll(Model model) {
		log.info("/chat/selectAll");
		
		List<ChatVO> list = service.selectAll();
		model.addAttribute("list", list);

		return "chat/selectAll";
	}

	@GetMapping("/chat/searchListParty")
	public String searchListParty(Model model, 
			@RequestParam(defaultValue = "01") int party_id) {
		log.info("/chat/searchListParty");
		log.info("searchWord:{}", party_id);


		List<ChatVO> list = service.searchListParty(party_id);
		log.info("list.size():{}", list.size());

		model.addAttribute("list", list);
		
		return "chat/selectAll";
	}

	@GetMapping("/chat/selectOne")
	public String selectOne(ChatVO vo, Model model) {
		log.info("/chat/selectOne");	
		log.info("vo:{}", vo);

		ChatVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "chat/selectOne";
	}

	@PostMapping("/chat/insertOK")
	public String insertOK(ChatVO vo) throws IllegalStateException, IOException {
		log.info("/chat/insertOK");
		log.info("vo:{}", vo);

		int result = service.insertOK(vo);
		log.info("result:{}", result);

			return "redirect:/event/calendar";

	}

	@PostMapping("/chat/updateOK")
	public String updateOK(ChatVO vo) throws IllegalStateException, IOException {
		log.info("/chat/updateOK");
		log.info("vo:{}", vo);



		int result = service.updateOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/chat/selectAll";
		} else {
			return "redirect:/chat/update" ;
		}
	}

	@PostMapping("/chat/deleteOK")
	public String deleteOK(ChatVO vo, Model model) {
		log.info("/chat/deleteOK");
		log.info("1vo:{}", vo);
		vo = service.selectOne(vo);
		log.info("2vo:{}", vo);
		int party_id = vo.getParty_id();
		int result = service.deleteOK(vo);
		log.info("result:{}", result);
		
		
		PartyVO room = (pservice.searchList("party_id", Integer.toString(party_id))).get(0);
        
        List<ChatVO> list = service.searchListParty(party_id);
		log.info("list.size():{}", list.size());

		model.addAttribute("chat_list", list);   
        model.addAttribute("room", room);
        log.info("/chat/chatroom : {}", party_id);
        return "room";
		
		
	}
	
	@GetMapping("/chat/room2/{id}")
    public String room(@PathVariable String id,
    		Model model) {
		log.info("/chat/chatroom : {}", id);
        PartyVO room = (pservice.searchList("party_id", id)).get(0);
        int party_id = Integer.parseInt(id);
        List<ChatVO> list = service.searchListParty(party_id);
		log.info("list.size():{}", list.size());

		model.addAttribute("chat_list", list);   
        model.addAttribute("room", room);
        log.info("/chat/chatroom : {}", id);
        return "room";
    }
	
	

}
