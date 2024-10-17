package partyDuo.com.controller;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.ChatService;
import partyDuo.com.service.EventLikeService;
import partyDuo.com.service.EventService;
import partyDuo.com.model.ChatVO;
import partyDuo.com.model.EventLikeVO;
import partyDuo.com.model.EventVO;

@Slf4j
@Controller
public class EventController {
	
	@Autowired
	EventService service;
	
	@Autowired
	EventLikeService service_like;
	
	@Autowired
	ChatService service_chat;
	

	@GetMapping("/event/insert")
	public String insert() {
		log.info("/event/insert");
		return "event/insert";
	}
	
	@GetMapping("/event/update")
	public String update(EventVO vo, Model model) {
		log.info("/event/update");		
		log.info("vo:{}", vo);

		EventVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "event/update";
	}

	@GetMapping("/event/delete")
	public String delete() {
		log.info("/event/delete");
		return "event/delete";
	}

	@GetMapping("/event/selectAll")
	public String selectAll(Model model) {
		log.info("/event/selectAll");
		
		List<EventVO> list = service.selectAll();
		model.addAttribute("list", list);

		return "event/selectAll";
	}

	@GetMapping("/event/searchListPartyMonth")
	public String searchListPartyMonth(Model model, 
			@RequestParam(defaultValue = "01") int party_id,
			@RequestParam(defaultValue = "10") String month) {
		
		if(month.equals("13")) {
			month="1";
		}
		log.info("/event/searchList");
		log.info("search_party_id:{}", party_id);
		log.info("month:{}", month);
		int month2 = Integer.parseInt(month);
		
		List<ChatVO> chat_list =service_chat.searchListParty(party_id);
		log.info("chat_list:{}", chat_list);
		
		List<EventVO> list = service.searchListPartyMonth(party_id, month2);
		log.info("list.size():{}", list.size());
		model.addAttribute("party_id", party_id);
		model.addAttribute("month", month2);
		model.addAttribute("chat_list", chat_list);
		model.addAttribute("list", list);
		
		return "event/calendar";
	}
	
	@GetMapping({"/event/calendar","/calendar"})
	public String searchListPartyMonthfirst(Model model, 
			@RequestParam(defaultValue = "01") int party_id,
			@RequestParam(defaultValue = "01") String month) {
		 LocalDate now = LocalDate.now();
		int month2 = now.getMonthValue();
		log.info("/event/searchList");

		log.info("search_party_id:{}", party_id);
		log.info("month2:{}", month2);
		
		List<ChatVO> chat_list =service_chat.searchListParty(party_id);
		log.info("chat_list:{}", chat_list);
		List<EventVO> list = service.searchListPartyMonth(party_id, month2);
		
		log.info("list.size():{}", list.size());
		model.addAttribute("party_id", party_id);
		model.addAttribute("month", month2);
		
		model.addAttribute("chat_list", chat_list);
		model.addAttribute("list", list);
		
		return "event/calendar";
	}
	
	@GetMapping("/event/searchListTitle")
	public String searchListTitle(Model model,
			@RequestParam(defaultValue = "01") String searchWord) {
		
		log.info("/event/searchListTitle");
		log.info("searchWord:{}", searchWord);


		List<EventVO> list = service.searchListTitle(searchWord);
		log.info("list.size():{}", list.size());

		model.addAttribute("list", list);
		
		return "event/calendar";
	}
	
	

	@GetMapping("/event/selectOne")
	public String selectOne(EventVO vo, Model model) {
		log.info("/event/selectOne");	
		log.info("vo:{}", vo);

		EventVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);
		
		EventLikeVO vo3 = new EventLikeVO();
		vo3.setEvent_id(vo2.getEvent_id());
		
		int likecount = service_like.selectOneLikeCount(vo3);
		log.info("likecount:{}", likecount);
		model.addAttribute("vo2", vo2);
		model.addAttribute("likecount", likecount);

		return "event/selectOne";
	}

	@PostMapping("/event/insertOK")
	public String insertOK(EventVO vo) throws IllegalStateException, IOException {
		log.info("/event/insertOK");
		log.info("vo:{}", vo);

		int result = service.insertOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/event/calendar";
		} else {
			return "redirect:/event/insert";
		}
	}

	@PostMapping("/event/updateOK")
	public String updateOK(EventVO vo) throws IllegalStateException, IOException {
		log.info("/event/updateOK");
		log.info("vo:{}", vo);



		int result = service.updateOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/event/calendar";
		} else {
			return "redirect:/event/update" ;
		}
	}

	@PostMapping("/event/deleteOK")
	public String deleteOK(EventVO vo) {
		log.info("/event/deleteOK");
		log.info("vo:{}", vo);

		int result = service.deleteOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/event/calendar";
		} else {
			return "redirect:/event/delete?event_id=" + vo.getEvent_id();
		}
	}
	
	

}
