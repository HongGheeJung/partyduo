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

import jakarta.servlet.http.HttpSession;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.ChatService;
import partyDuo.com.service.EventLikeService;
import partyDuo.com.service.EventService;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;
import partyDuo.com.model.ChatVO;
import partyDuo.com.model.EventLikeVO;
import partyDuo.com.model.EventVO;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.MyPartyVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;

@Slf4j
@Controller
public class EventController {
	
	@Autowired
	EventService service;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	EventLikeService service_like;
	
	@Autowired
	ChatService service_chat;
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	PartyListService plservice;
	
	@Autowired
	PartyService pservice;
	

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

	@GetMapping("/event/searchListParty")
	public String searchListParty(Model model, 
			@RequestParam(defaultValue = "01") int party_id) {
		
		log.info("event/searchListParty");
		log.info("party_id:{}", party_id);
		MemberVO vo = new MemberVO();
		vo.setId((String)session.getAttribute("user_id"));
		vo=mservice.member_selectOne(vo);
		int member_id=vo.getMember_id();
		List<PartyListVO> plist = plservice.searchListJoinMember(Integer.toString(member_id));
		log.info("plist:{}", plist);	
		model.addAttribute("party_id", party_id);
		model.addAttribute("plist", plist);
		
		return "cindex";
	}

	@GetMapping("/cindex")
	public String searchListfirst(Model model, 
			@RequestParam(defaultValue = "0") int party_id) {
		log.info("/cindex");
		
		MemberVO vo = new MemberVO();
		vo.setId((String)session.getAttribute("user_id"));
		vo=mservice.member_selectOne(vo);
		int member_id=vo.getMember_id();
		List<MyPartyVO> plist = plservice.searchMyParty(Integer.toString(member_id), 1, 100);
		log.info("plist:{}", plist);
		if(party_id==0) {
			party_id = plist.get(0).getParty_id();
		}
		PartyVO partyvo = (pservice.searchList("party_id", Integer.toString(party_id))).get(0);
		
		
		log.info("partyvo", partyvo);
		model.addAttribute("partyvo", partyvo);
		model.addAttribute("plist", plist);
		
		return "cindex";
	}
	
	@GetMapping("/event/searchListTitle")
	public String searchListTitle(Model model,
			@RequestParam(defaultValue = "01") String searchWord) {
		
		log.info("/event/searchListTitle");
		log.info("searchWord:{}", searchWord);


		List<EventVO> list = service.searchListTitle(searchWord);
		log.info("list.size():{}", list.size());

		model.addAttribute("list", list);
		
		return "event/searchlist";
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
			return "redirect:/cindex";
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
			return "redirect:/cindex";
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
			return "redirect:/cindex";
		} else {
			return "redirect:/event/delete?event_id=" + vo.getEvent_id();
		}
	}
	
	

}
