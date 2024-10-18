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
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.MyPartyVO;
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
	
	@GetMapping("/partylist/application")
	public String insert(PartyListVO vo,Model model) {
		log.info("party_list_application...");
		log.info("vo:{}", vo);
		PartyVO vo2 = new PartyVO();
		vo2.setParty_id(vo.getParty_id());
		vo2=pservice.selectOne(vo2);
		model.addAttribute("vo2", vo2);
		return "partylist/application";			
	}
	
	@PostMapping("/partylist/applicationOK")
	public String insertOK(PartyVO vo) {
		log.info("party_list_applicationOK...");
		log.info("vo:{}", vo);
		PartyListVO vo2= new PartyListVO();
		
		MemberVO vo3 = new MemberVO();
		vo3.setId((String)session.getAttribute("user_id"));
		vo3=mservice.member_selectOne(vo3);
		log.info("vo3:{}", vo3);
		vo2.setMember_id(vo3.getMember_id());
		vo2.setParty_id(vo.getParty_id());
		int result=plservice.insertOK(vo2);
		log.info("result:{}", result);
		return "redirect:/partylist/myparty";			
	}
	
	@GetMapping("/partylist/accept")
	public String accept(PartyListVO vo,Model model) {
		log.info("party_list_update...");
		PartyListVO vo2 = plservice.selectOne(vo);
		MemberVO vo3= new MemberVO();
		vo3.setMember_id(vo.getMember_id());
		vo3=mservice.member_selectOneByMember_id(vo3);
		
		model.addAttribute("vo2", vo2);
		model.addAttribute("vo3", vo3);
		return "partylist/accept";			
	}
	
	@PostMapping("/partylist/acceptOK")
	public String acceptOK(PartyListVO vo) {
		log.info("party_list_updateOK...");
		int result=plservice.updateOK(vo);
		return "redirect:/partylist/selectOne?party_id="+vo.getParty_id();			
	}
	
	@GetMapping("/partylist/deny")
	public String delete(PartyListVO vo,Model model) {
		log.info("party_list_deny...");
		PartyListVO vo2 = plservice.selectOne(vo);
		MemberVO vo3= new MemberVO();
		vo3.setMember_id(vo.getMember_id());
		vo3=mservice.member_selectOneByMember_id(vo3);
		
		model.addAttribute("vo2", vo2);
		model.addAttribute("vo3", vo3);
		return "partylist/deny";			
	}
	
	@PostMapping("/partylist/denyOK")
	public String deleteOK(PartyListVO vo) {
		log.info("party_list_deleteOK...");
		int result=plservice.deleteOK(vo);
		return "redirect:/partylist/selectOne?party_id="+vo.getParty_id();			
	}
	
	@GetMapping("/partylist/selectOne")
	public String selectOne(int party_id,Model model) {
		log.info("party_list_selectOne...");
		PartyVO vo= new PartyVO();
		MemberVO vo3= new MemberVO();
		List<MemberVO> listmember= new ArrayList<>();
		List<MemberVO> listqueue= new ArrayList<>();
		vo.setParty_id(party_id);
		vo=pservice.selectOne(vo);
		//파티 아이디, 파티 이름, 파티 장
		List<PartyListVO> list = plservice.searchList("party_id", Integer.toString(party_id));
		log.info("list{}",list);
		
		for (PartyListVO vo2 : list) {
			if (vo2.getParty_join()==true) {
				vo3.setMember_id(vo2.getMember_id());
				vo3=mservice.member_selectOneByMember_id(vo3);
				log.info("vo3{}", vo3);
				listmember.add(vo3);
			}else if(vo2.getParty_join()==false) {
				vo3.setMember_id(vo2.getMember_id());
				log.info("vo3{}", vo3);
				vo3=mservice.member_selectOneByMember_id(vo3);
				log.info("vo3{}", vo3);
				listqueue.add(vo3);
			}
		}
		
		log.info("vo{}", vo);
		log.info("listmember{}",listmember);
		log.info("listqueue{}",listqueue);
		model.addAttribute("vo", vo);
		model.addAttribute("listmember", listmember);
		model.addAttribute("listqueue", listqueue);
		return "partylist/selectOne";			
	}
	
	@GetMapping("partylist/myparty")
	public String myparty(Model model,@RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5")int pageBlock) {
		MemberVO vo = new MemberVO();
		vo.setId((String)session.getAttribute("user_id"));
		String character_name=(String) session.getAttribute("user_character");
		vo=mservice.member_selectOne(vo);
		int member_id=vo.getMember_id();//현재 로그인유저의 멤버아이디
		log.info("member_id:{}",member_id);
		//partylist search 수정
		List<MyPartyVO> list = plservice.searchMyParty(Integer.toString(member_id),cpage,pageBlock);
		log.info("list:{}", list);
		model.addAttribute("list", list);
		
		int total_rows = plservice.getTotalPartyListRows(Integer.toString(member_id));
		log.info("total_rows:{}", total_rows);
		// int pageBlock = 5;//1개페이지에서 보여질 행수,파라메터로 받으면됨.
		int totalPageCount = 0;

		// 총행카운트와 페이지블럭을 나눌때의 알고리즘을 추가기
		if (total_rows / pageBlock == 0) {
			totalPageCount = 1;
		} else if (total_rows % pageBlock == 0) {
			totalPageCount = total_rows / pageBlock;
		} else {
			totalPageCount = total_rows / pageBlock + 1;
		}
		log.info("totalPageCount:{}", totalPageCount);

		model.addAttribute("totalPageCount", totalPageCount);
		return "partylist/myparty";
	}
	
}
