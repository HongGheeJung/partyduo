package partyDuo.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.PartyBoardVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyBoardService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;

@Slf4j
@Controller
public class PartyListController {
	@Autowired
	HttpSession session;
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	PartyService pservice;
	
	@Autowired
	PartyBoardService pbservice;
	
	@Autowired
	PartyListService plservice;
	
	@GetMapping("/partylist/insert")
	public String insert(PartyListVO vo,Model model,int party_board_id) {
		log.info("party_list_insert...");
		log.info("vo:{}", vo);
		
		//vo2가 partylist가 아니라 partyboard 가 되어야할듯?
		
		return "partylist/insert";			
	}
	
	@PostMapping("/partylist/insertOK")
	public String insertOK(PartyListVO vo) {
		log.info("party_list_insertOK...");
		log.info("vo:{}", vo);
		int result=plservice.insertOK(vo);
		log.info("result:{}", result);
		return "redirect:/partylist/searchList";			
	}
	
	@GetMapping("/partylist/update")
	public String update(PartyListVO vo,Model model) {
		log.info("party_list_update...");
		PartyListVO vo2 = plservice.selectOne(vo);
		
		model.addAttribute("vo2", vo2);
		
		return "partylist/update";			
	}
	
	@PostMapping("/partylist/updateOK")
	public String updateOK(PartyListVO vo) {
		log.info("party_list_updateOK...");
		int result=plservice.updateOK(vo);
		return "redirect:/partylist/searchList";			
	}
	
	@GetMapping("/partylist/delete")
	public String delete(PartyListVO vo,Model model) {
		log.info("party_list_delete...");
		PartyListVO vo2 = plservice.selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "partylist/delete";			
	}
	
	@PostMapping("/partylist/deleteOK")
	public String deleteOK(PartyListVO vo) {
		log.info("party_list_deleteOK...");
		int result=plservice.deleteOK(vo);
		return "redirect:/partylist/searchList";			
	}
	
	@GetMapping("/partylist/searchList")
	public String searchList(Model model, @RequestParam(defaultValue="party_id") String searchKey,
			@RequestParam(defaultValue="13")String searchWord) {
		log.info("party_list_searchList...");
		List<PartyListVO> list = plservice.searchList(searchKey,searchWord);
		model.addAttribute("list",list);
		log.info("list: {}", list);
		return "partylist/selectAll";			
	}
	
	@GetMapping("partylist/myparty")
	public String myparty(String searchKey,String searchWord,@RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5")int pageBlock,Model model) {
		MemberVO vo = null;
		vo.setId((String)session.getAttribute("user_id"));
		String character_name=(String) session.getAttribute("user_character");
		MemberVO vo2=mservice.member_selectOne(vo);
		int member_id=vo2.getMember_id();
		
		List<PartyListVO> list = plservice.searchListPageBlock("member_id",Integer.toString(member_id), cpage, pageBlock);
		List<PartyVO> list2=pservice.searchList("party_master",character_name);
		
		model.addAttribute("list",list);
		model.addAttribute("list2",list2);
		return "partylist/myparty";
	}
	
}
