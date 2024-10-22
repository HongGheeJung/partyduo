package partyDuo.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;

@Slf4j
@Controller
public class PartyController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	PartyService pservice;
	
	@Autowired
	PartyListService plservice;
	
	@Autowired
	MemberService mservice;
	
	@GetMapping("/party/insert")
	public String insert() {
		log.info("party_insert...");
		return "party/insert";			
	}
	
	@PostMapping("/party/insertOK")
	public String insertOK(PartyVO vo) {
		log.info("party_insertOK...");
		log.info("vo:{}", vo);
		int result = pservice.insertOK(vo);
		log.info("result:{}", result);
		
		PartyListVO vo2 = new PartyListVO();
		MemberVO vo3 = new MemberVO();
		vo3.setId((String)session.getAttribute("user_id"));
		vo3=mservice.member_selectOne(vo3);
		int member_id = vo3.getMember_id();
		vo= pservice.selectOnePM(vo);
		vo2.setParty_id(vo.getParty_id());
		vo2.setMember_id(member_id);
		vo2.setParty_join(true);
		int result2=plservice.insertOKPartyMaster(vo2);
		log.info("result2:{}", result2);
		return "redirect:/partylist/myparty";	
	}
	
	@GetMapping("/party/update")
	public String update(PartyVO vo,Model model) {
		log.info("party_update...");
		PartyVO vo2 = pservice.selectOne(vo);
		log.info("{vo:{}",vo2);
		model.addAttribute("vo2", vo2);
		return "party/update";			
	}
	
	@GetMapping("/party/delegate")
	public String delegate(PartyVO vo,Model model) {
		log.info("party_delegate...");
		PartyVO vo2 = pservice.selectOne(vo);
		List<MemberVO> listmember= new ArrayList<>();
		List<PartyListVO> list = plservice.searchList("party_id", Integer.toString(vo.getParty_id()));
		for (PartyListVO vo3 : list) {
			if (vo3.getParty_join()) {
				MemberVO vo4= new MemberVO();
				vo4.setMember_id(vo3.getMember_id());
				vo4=mservice.member_selectOneByMember_id(vo4);
				log.info("vo3{}", vo4);
				listmember.add(vo4);
			}
		}
		log.info("{vo:{}",vo2);
		model.addAttribute("vo2", vo2);
		model.addAttribute("listmember", listmember);
		return "party/delegate";			
	}
	
	@PostMapping("/party/updateOK")
	public String updateOK(PartyVO vo) {
		log.info("party_updateOK...");
		int result = pservice.updateOK(vo);
		log.info("result:{}", result);
		return "redirect:/party/selectOne?party_id=" + vo.getParty_id();
	}
	
	@GetMapping("/party/delete")
	public String delete(PartyVO vo,Model model) {
		log.info("party_delete...");
		PartyVO vo2 = pservice.selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "party/delete";			
	}
	
	@PostMapping("/party/deleteOK")
	public String deleteOK(PartyVO vo) {
		log.info("party_deleteOK...");
		
		int result = pservice.deleteOK(vo);
		log.info("result:{}", result);
		
		return "redirect:/partylist/myparty";			
	}
	
	@GetMapping("/party/selectOne")
	public String selectOne(PartyVO vo, Model model) {
		log.info("party_selectOne...");
		log.info("vo:{}", vo);
		PartyVO vo2 = pservice.selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);
		return "party/selectOne";			
	}
	
	@GetMapping("/party/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5")int pageBlock) {
		log.info("/party_selectAll");
		List<PartyVO> list=pservice.selectAll(cpage, pageBlock);
		log.info("list: {}", list);
		model.addAttribute("list", list);
		return "party/selectAll";
	}
	
	@GetMapping("/party/searchList")
	public String searchList(Model model, @RequestParam(defaultValue="party_master") String searchKey,
			@RequestParam(defaultValue="ad")String searchWord) {
		log.info("party_searchList...");
		
		List<PartyVO> list = pservice.searchList(searchKey,searchWord);
		model.addAttribute("list",list);
		log.info("list: {}", list);
		return "party/selectAll";			
	}

}
