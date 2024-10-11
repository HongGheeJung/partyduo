package partyDuo.com.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.MemberService;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	MemberService service;
	
	MemberVO vo=new MemberVO();
	
	@GetMapping("/member/insert")
	public String member_insert() {
		log.info("/insert");
		return "member/insert";
	}
	@GetMapping("/member/insertOK")
	public String member_insertOK() {
		log.info("/insertOK");
		int result=service.member_insert(vo);
		log.info("result: {}", result);
		return "member/selectLogin";
	}
	@GetMapping("/member/update")
	public String member_update() {
		log.info("/update");
		return "member/update";
	}
	@GetMapping("/member/updateOK")
	public String member_updateOK() {
		log.info("/updateOK");
		int result=service.member_update(vo);
		log.info("result: {}", result);
		return "main";
	}
	@GetMapping("/member/delete")
	public String member_delete() {
		log.info("/delete");
		
		return "member/delete";
	}
	@GetMapping("/member/deleteOK")
	public String member_deleteOK() {
		log.info("/deleteOK");
		int result=service.member_delete(vo);
		log.info("result: {}", result);
		return "main";
	}
	@GetMapping("/member/selectOne")
	public String member_selectOne() {
		log.info("/selectOne");
		MemberVO vo2=service.member_selectOne(vo);
		log.info("vo2: {}",vo2);
		return "member/selectOne";
	}
	@GetMapping("/member/selectAll")
	public String member_selectAll() {
		log.info("/selectAll");
		List<MemberVO> list=service.member_selectAll();
		log.info("list: {}", list);
		return "member/selectAll";
	}
	@GetMapping("/member/searchList")
	public String member_searchList() {
		String searchKey="a";
		String searchWord="b";
		log.info("/searchList");
		List<MemberVO> list=service.member_searchList(searchKey, searchWord);
		log.info("list: {}", list);
		return "member/searchList";
	}
	@GetMapping("/member/login")
	public String member_login() {
		log.info("/login");
		return "member/login";
	}
	@GetMapping("/member/loginOK")
	public String member_loginOK() {
		log.info("/loginOK");
		int result=service.member_login(vo);
		log.info("result: {}", result);
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
>>>>>>> 758e117e2650bde1fff7a90050edea68436df014
