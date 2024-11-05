package partyDuo.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.AdminVO;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.AdminService;
import partyDuo.com.service.FavoriteService;
import partyDuo.com.service.HashService;
import partyDuo.com.service.MemberService;

@Slf4j
@Controller
public class MemberController {

	@Autowired

	HttpSession session;

	@Autowired
	MemberService service;

	@Autowired
	FavoriteService fservice;



	@Autowired
	AdminService adservice;

	MemberVO vo;

	@GetMapping("/member/insert")
	public String member_insert() {
		log.info("/insert");
		return "member/insert";
	}

	@PostMapping("/member/insertOK")
	public String member_insertOK(MemberVO vo, Model model) {
		log.info("/insertOK");
//		vo=new MemberVO();
//		vo.setId("admin2");
//		vo.setPw("hi1234");
//		vo.setEmail("abc@efg.com");
//		vo.setApikey("0123456789");
//		vo.setCharacter_name("테스트2");
		try {
			log.info("vo: {}", vo);
			String salt=HashService.Salt();
			log.info("salt:{}",salt);
			vo.setSalt(salt);
			String password=HashService.getSHA512(vo.getPw(), salt);
			vo.setPw(password);
			int result=service.member_insert(vo);
			log.info("result: {}", result);
			if (result==1) {
				return "member/selectLogin";
			}else {
				return "redirect:/member/insert";
			}
		} catch (Exception e) {
			String joinResult="Failed";
			model.addAttribute("joinResult", joinResult);
			return "member/insert";
		}
		
		
	}

	@GetMapping("/member/update")
	public String member_update(MemberVO vo, Model model) {
		log.info("/update");
		MemberVO vo2 = service.member_selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "member/update";
	}

	@PostMapping("/member/updateOK")
	public String member_updateOK(MemberVO vo) {
		log.info("/updateOK");
//		vo=new MemberVO();
//		vo.setMember_id(1);
//		vo.setId("kim");
//		vo.setPw("hello1919");
//		vo.setEmail("abc@efg.com");
//		vo.setApikey("987654321");
//		vo.setCharacter_name("update테스트");
		int result = service.member_update(vo);
		log.info("result: {}", result);
		if (result != 0) {
			return "redirect:/main";
		} else {
			return "redirect:/member/update?id=" + vo.getId();
		}
	}

	@GetMapping("/member/delete")
	public String member_delete(MemberVO vo, Model model) {
		log.info("/delete");
		model.addAttribute("vo", vo);
		return "member/delete";
	}

	@GetMapping("/member/deleteCheck")
	public String member_deleteCheck(MemberVO vo, Model model) {
		log.info("deleteCheck");
		log.info("vo: {}", vo);
		MemberVO vo2 = service.member_selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);
		return "member/deleteCheck";
	}

	@PostMapping("/member/deleteOK")
	public String member_deleteOK(MemberVO vo, String pwCheck, Model model) {
		log.info("/deleteOK");
		log.info("vo:{}", vo);
		String hexPw=HashService.getSHA512(pwCheck, vo.getSalt());
		log.info("hexPw:{}",hexPw);		
		if(hexPw.equals(vo.getPw())) {
			int result = service.member_delete(vo);
			log.info("result: {}", result);
			if (result != 0) {
				session.removeAttribute("user_character");
				session.removeAttribute("admin_name");
				session.removeAttribute("user_id");
				return "redirect:/main";
			} else {
				model.addAttribute("errorMessage","비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
				model.addAttribute("vo2",service.member_selectOne(vo));
				return "member/deleteCheck";
			}
		}else {
			model.addAttribute("errorMessage","비밀번호가 일치하지 않습니다. 다시 시도해주세요.");
			model.addAttribute("vo2",service.member_selectOne(vo));
			return "member/deleteCheck";
		}
	}

	@PostMapping("/member/deleteByAdmin")
	public String member_deleteByAdmin(MemberVO vo) {
		log.info("관리자 권한으로 삭제");
		MemberVO vo2 = service.member_selectOne(vo);
		int result = service.member_delete(vo2);
		return "redirect:/main";
	}

	@GetMapping("/member/selectAll")
	public String member_selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5") int pageBlock) {
		log.info("/selectAll");
		if (session.getAttribute("admin_name") == null) {
			return "main";
		}
		List<MemberVO> list = service.member_selectAll(cpage, pageBlock);
		log.info("list: {}", list);

		int total_rows = service.getTotalRows();// select count(*) total_rows from member;
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

		model.addAttribute("list", list);
		return "member/selectAll";
	}

	@GetMapping("/member/searchList")
	public String member_searchList(Model model, @RequestParam(defaultValue = "id") String searchKey,
			@RequestParam(defaultValue = "ad") String searchWord, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5") int pageBlock) {
		log.info("/searchList");
		List<MemberVO> list = service.member_searchList(searchKey, searchWord, cpage, pageBlock);
		model.addAttribute("list", list);

		int total_rows = service.getSearchTotalRows(searchKey, searchWord);// select count(*) total_rows from member;
		log.info("total_rows:{}", total_rows);
		log.info("list: {}", list);

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

		return "member/selectAll";
	}

	@GetMapping("/member/login")
	public String member_login() {
		log.info("/login");
		return "member/login";
	}

	@PostMapping("/member/loginOK")
	public String member_loginOK(MemberVO vo, Model model) {
		log.info("/loginOK");
		MemberVO vo2 = service.member_login(vo);
		if (vo2 == null) {

			model.addAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "member/login";
		}
		AdminVO vo3 = new AdminVO();
		vo3.setId(vo2.getId());
		log.info("vo3: {}", vo3);
		vo3 = adservice.selectOne(vo3);
		log.info("vo2: {}", vo2);
		log.info("vo3: {}", vo3);

		if (vo3 != null && vo3.getAdmin_id() > 0 && vo2.getId().equals(vo3.getId())) {
			session.setAttribute("user_id", vo2.getId());
			session.setAttribute("user_character", vo2.getCharacter_name());
			session.setAttribute("admin_name", vo3.getName());
			return "redirect:/main";
		} else if (vo3 == null) { // vo3가 null이 아닐 경우 추가 조건
			session.setAttribute("user_id", vo2.getId());
			session.setAttribute("user_character", vo2.getCharacter_name());
			return "redirect:/main";
		} else {
			// vo3가 null일 경우에 대한 처리 (원하는 경우)
			return "member/login"; // 또는 다른 페이지로 리다이렉트
		}
	}

	@GetMapping("/member/logout")
	public String member_logout() {
		log.info("/loginout");
		session.removeAttribute("user_id");
		session.removeAttribute("user_character");
		session.removeAttribute("admin_name");
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

	@PostMapping("/member/findPwCheck")
	public String member_findPwCheck(Model model, MemberVO vo) throws Exception {
		log.info("/findPwCheck");
//		vo=new MemberVO();
//		vo.setId("admin");
		MemberVO vo2 = service.member_selectOne(vo);
		String result = service.member_findPwCheck(vo2);
		log.info("result: {}", result);
		model.addAttribute("vo2", vo2);
		if (result == null) {
			return "redirect:/member/findPw";
		} else {
			return "member/findPwResult";
		}
	}

	@PostMapping("/member/findIDCheck")
	public String member_findIDCheck(Model model, MemberVO vo) {
		log.info("/findIDCheck");
//		vo=new MemberVO();
//		vo.setEmail("abc@def.com");
		List<String> list = service.member_findIDCheck(vo);
		log.info("list: {}", list);
		model.addAttribute("list", list);
		if (list == null) {
			return "redirect:/member/findId";
		} else {

			return "member/findIdResult";
		}
	}

	@GetMapping("/member/pwChange")
	public String member_pwChange(Model model, MemberVO vo) {
		log.info("/selectOne");
		MemberVO vo2 = service.member_selectOne(vo);
		model.addAttribute("vo2", vo2);
		log.info("pw Change vo2: {}", vo2);
		return "member/pwChange";
	}

	@PostMapping("/member/pwChangeOK")
	public String member_pwChange(Model model, MemberVO vo, String oldpw) {
		log.info("vo: {}", vo);
		log.info("oldpw: {}", oldpw);
		MemberVO vo2 = service.member_selectOne(vo);
		log.info("vo2:{}", vo2);
		String salt=vo2.getSalt();
		oldpw=HashService.getSHA512(oldpw, salt);
		log.info("oldpw:{}", oldpw);
		String oldpwCheck = vo2.getPw();
		log.info("oldpwCheck:{}",oldpwCheck);
		int result = service.member_pwChange(vo, oldpw, oldpwCheck, salt);
		if (result == 0) {
			log.info("youfailed...");
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("vo2", vo2);
			return "member/pwChange";
		}
		return "redirect:/member/update?id=" + vo.getId();
	}
}