package partyDuo.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.MemberService;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	MemberService service;
	
	MemberVO vo;
	
	@GetMapping("/member/insert")
	public String member_insert() {
		log.info("/insert");
		return "member/insert";
	}
	@GetMapping("/member/insertOK")
	public String member_insertOK(MemberVO vo) {
		log.info("/insertOK");
//		vo=new MemberVO();
//		vo.setId("admin2");
//		vo.setPw("hi1234");
//		vo.setEmail("abc@efg.com");
//		vo.setApikey("0123456789");
//		vo.setCharacter_name("테스트2");
		log.info("vo: {}", vo);
		int result=service.member_insert(vo);
		log.info("result: {}", result);
		return "member/selectLogin";
	}
	@GetMapping("/member/update")
	public String member_update(MemberVO vo, Model model) {
		log.info("/update");
		MemberVO vo2=service.member_selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "member/update";
	}
	@GetMapping("/member/updateOK")
	public String member_updateOK(MemberVO vo) {
		log.info("/updateOK");
//		vo=new MemberVO();
//		vo.setMember_id(1);
//		vo.setId("kim");
//		vo.setPw("hello1919");
//		vo.setEmail("abc@efg.com");
//		vo.setApikey("987654321");
//		vo.setCharacter_name("update테스트");
		int result=service.member_update(vo);
		log.info("result: {}", result);
		return "redirect:/member/selectOne?member_id="+vo.getMember_id();
	}
	@GetMapping("/member/delete")
	public String member_delete(MemberVO vo, Model model) {
		log.info("/delete");
		model.addAttribute("vo", vo);
		return "member/delete";
	}
	@GetMapping("/member/deleteOK")
	public String member_deleteOK(MemberVO vo) {
		log.info("/deleteOK");
//		vo=new MemberVO();
//		vo.setMember_id(1);
		int result=service.member_delete(vo);
		log.info("result: {}", result);
		return "redirect:/member/selectAll";
	}
	@GetMapping("/member/selectOne")
	public String member_selectOne(Model model,MemberVO vo) {
		log.info("/selectOne");
		MemberVO vo2=service.member_selectOne(vo);
		model.addAttribute("vo2", vo2);
		log.info("vo2: {}",vo2);
		return "member/selectOne";
	}
	@GetMapping("/member/selectAll")
	public String member_selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5")int pageBlock) {
		log.info("/selectAll");
		List<MemberVO> list=service.member_selectAll(cpage, pageBlock);
		log.info("list: {}", list);
		
		model.addAttribute("list", list);
		return "member/selectAll";
	}
	@GetMapping("/member/searchList")
	public String member_searchList(Model model, @RequestParam(defaultValue="id") String searchKey,
			@RequestParam(defaultValue="ad")String searchWord, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5")int pageBlock) {
		log.info("/searchList");
		List<MemberVO> list=service.member_searchList(searchKey, searchWord, cpage, pageBlock);
		model.addAttribute("list", list);
		log.info("list: {}", list);
		return "member/selectAll";
	}
	@GetMapping("/member/login")
	public String member_login() {
		log.info("/login");
		return "member/login";
	}
	@GetMapping("/member/loginOK")
	public String member_loginOK(MemberVO vo) {
		log.info("/loginOK");
		MemberVO vo2=service.member_login(vo);
		log.info("vo2: {}", vo2);
		if(vo2==null) {
			return "member/login";
		}else {
			session.setAttribute("user_id", vo2.getId());
			session.setAttribute("user_character", vo2.getCharacter_name());
			return "main";
		}	
	}
	@GetMapping("/member/logout")
	public String member_logout() {
		log.info("/loginout");
		session.removeAttribute("user_id");
		session.removeAttribute("user_character");
		return "main";
	}
	@GetMapping("/member/findPw")
	public String member_findPw() {
		log.info("/findPw");
		return "member/findPw";
	}
	@GetMapping("/member/findID")
	public String member_findID() {
		log.info("/findID");
		return "member/findID";
	}
	@GetMapping("/member/findPwCheck")
	public String member_findPwCheck() {
		log.info("/findPwCheck");
		int result=service.member_findPwCheck(vo);
		log.info("result: {}", result);
		return "member/findPwResult";
	}
	@GetMapping("/member/findIDCheck")
	public String member_findIDCheck() {
		log.info("/findIDCheck");
		int result=service.member_findIDCheck(vo);
		log.info("result: {}", result);
		return "member/findIDResult";
	}
}