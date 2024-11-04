package partyDuo.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.MyPartyVO;
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
	    public String insert(PartyListVO vo, Model model, RedirectAttributes redirectAttributes) {
	        log.info("party_list_application...");
	        log.info("vo:{}", vo);

	        // party_id 검증
	        if (vo == null || vo.getParty_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	            return "redirect:/partylist/myparty";
	        }

	        try {
	            PartyVO vo2 = new PartyVO();
	            vo2.setParty_id(vo.getParty_id());
	            vo2 = pservice.selectOne(vo2);
	        
	        String user_character = (String) session.getAttribute("user_character");
	        
	        // user_character가 null이거나 비어있을 경우 오류 처리
	        if (user_character == null || user_character.trim().isEmpty()) {
	            model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다. 다시 로그인해 주세요.");
	            return "redirect:/member/login";  // 로그인 페이지로 리다이렉트
	        }
	        
	        MemberVO vo3 = new MemberVO();
            vo3.setId((String) session.getAttribute("user_id"));
            log.info((String) session.getAttribute("user_id"));
            log.info("vo:{}", vo3);
            vo3 = mservice.member_selectOne(vo3);
            int member_id = vo3.getMember_id();
            log.info("vo3:{}", vo3);
            vo.setMember_id(member_id);
            PartyListVO vo4 = plservice.selectOne(vo);
            String status;
            if (vo4 == null) {
                status = "noapplication";
            } else if (vo4.getParty_join() == true) {
                status = "accepted";
            } else {
                status = "application";
            }
            model.addAttribute("status", status);
            model.addAttribute("vo2", vo2);
        } catch (Exception e) {
            log.error("데이터베이스 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "redirect:/partylist/myparty";
        }

        return "partylist/application";
    }

	 @PostMapping("/partylist/applicationOK")
	    public String insertOK(PartyVO vo, RedirectAttributes redirectAttributes) {
	        log.info("party_list_applicationOK...");
	        log.info("vo:{}", vo);

	        // party_id 검증
	        if (vo == null || vo.getParty_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	            return "redirect:/partylist/myparty";
	        }
        try {
            PartyListVO vo2 = new PartyListVO();
            MemberVO vo3 = new MemberVO();
	        
	        String user_character = (String) session.getAttribute("user_character");
	        
	        // user_character가 null이거나 비어있을 경우 오류 처리
	        if (user_character == null || user_character.trim().isEmpty()) {
	        	redirectAttributes.addFlashAttribute("errorMessage", "로그인 정보가 유효하지 않습니다. 다시 로그인해 주세요.");
	            return "redirect:/member/login";  // 로그인 페이지로 리다이렉트
	        }
	        
	        vo3.setId((String) session.getAttribute("user_id"));
            vo3 = mservice.member_selectOne(vo3);
            log.info("vo3:{}", vo3);
            vo2.setMember_id(vo3.getMember_id());
            vo2.setParty_id(vo.getParty_id());
            int result = plservice.insertOK(vo2);
            log.info("result:{}", result);
        } catch (Exception e) {
            log.error("데이터베이스 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "파티 신청 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "redirect:/party/selectAll";
        }
        redirectAttributes.addFlashAttribute("successMessage", "success");
        return "redirect:/partylist/application?party_id="+vo.getParty_id();
    }

	 @GetMapping("/partylist/accept")
	    public String accept(PartyListVO vo, Model model, RedirectAttributes redirectAttributes) {
	        log.info("party_list_update...");

	        // party_id 검증
	        if (vo == null || vo.getParty_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	            return "redirect:/partylist/myparty";
	        }

	        try {
	            PartyListVO vo2 = plservice.selectOne(vo);
	            MemberVO vo3 = new MemberVO();
	            vo3.setMember_id(vo.getMember_id());
	            vo3 = mservice.member_selectOneByMember_id(vo3);

	            model.addAttribute("vo2", vo2);
	            model.addAttribute("vo3", vo3);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/partylist/myparty";
	        }

	        return "partylist/accept";
	    }

	 @PostMapping("/partylist/acceptOK")
	    public String acceptOK(PartyListVO vo, RedirectAttributes redirectAttributes) {
	        log.info("party_list_updateOK...");

	        // party_id 검증
	        if (vo == null || vo.getParty_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	            return "redirect:/partylist/selectOne?party_id=" + vo.getParty_id();
	        }

	        try {
	            int result = plservice.updateOK(vo);
	            log.info("result: {}", result);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 수락 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/partylist/selectOne?party_id=" + vo.getParty_id();
	        }
	        redirectAttributes.addFlashAttribute("successMessage", "success");
	        return "redirect:/partylist/accept?party_id=" + vo.getParty_id()+"&member_id="+vo.getMember_id();
	    }

	 @GetMapping("/partylist/deny")
	    public String delete(PartyListVO vo, Model model, RedirectAttributes redirectAttributes) {
	        log.info("party_list_deny...");

	        // party_id 검증
	        if (vo == null || vo.getParty_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	            return "redirect:/partylist/myparty";
	        }

	        try {
	            PartyListVO vo2 = plservice.selectOne(vo);
	            MemberVO vo3 = new MemberVO();
	            vo3.setMember_id(vo.getMember_id());
	            vo3 = mservice.member_selectOneByMember_id(vo3);
	            PartyVO vo4 = new PartyVO();
	            vo4.setParty_id(vo2.getParty_id());
	            vo4 = pservice.selectOne(vo4);

	            model.addAttribute("vo2", vo2);
	            model.addAttribute("vo3", vo3);
	            model.addAttribute("vo4", vo4);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 거절 처리 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/partylist/myparty";
	        }

	        return "partylist/deny";
	    }

		 @GetMapping("/partylist/denyself")
		    public String deleteself(PartyListVO vo, Model model, RedirectAttributes redirectAttributes) {
		        log.info("party_list_denyself...");

		        // party_id 검증
		        if (vo == null || vo.getParty_id() == 0) {
		            redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
		            return "redirect:/partylist/myparty";
		        }

		        PartyListVO vo2 = plservice.selectOne(vo);
		        MemberVO vo3 = new MemberVO();
		        vo3.setMember_id(vo.getMember_id());
		        vo3 = mservice.member_selectOneByMember_id(vo3);
		        PartyVO vo4 = new PartyVO();
		        vo4.setParty_id(vo2.getParty_id());
		        vo4 = pservice.selectOne(vo4);

		        model.addAttribute("vo2", vo2);
		        model.addAttribute("vo3", vo3);
		        model.addAttribute("vo4", vo4);
		        return "partylist/denyself";
		    }

		 @PostMapping("/partylist/denyOK")
		    public String deleteOK(PartyListVO vo, RedirectAttributes redirectAttributes,String from,Model model) {
		        log.info("party_list_deleteOK...");

		        // party_id 검증
		        if (vo == null || vo.getParty_id() == 0) {
		            redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
		            if ("denyself".equals(from)) {
				        return "redirect:/partylist/denyself?party_id=" + vo.getParty_id()+"&member_id="+vo.getMember_id();
				    } else {
				        return "redirect:/partylist/deny?party_id=" + vo.getParty_id()+"&member_id="+vo.getMember_id();
				    }
		        }

		        try {
		        	PartyListVO vo2 = plservice.selectOne(vo);
			        MemberVO vo3 = new MemberVO();
			        vo3.setMember_id(vo.getMember_id());
			        vo3 = mservice.member_selectOneByMember_id(vo3);
			        PartyVO vo4 = new PartyVO();
			        vo4.setParty_id(vo2.getParty_id());
			        vo4 = pservice.selectOne(vo4);

			        model.addAttribute("vo2", vo2);
			        model.addAttribute("vo3", vo3);
			        model.addAttribute("vo4", vo4);
		        	
		            int result = plservice.deleteOK(vo);
		            log.info("result: {}", result);
     
		        } catch (Exception e) {
		            log.error("데이터베이스 오류 발생: {}", e.getMessage());
		            redirectAttributes.addFlashAttribute("errorMessage", "파티 거절 중 오류가 발생했습니다. 다시 시도해 주세요.");
		            if ("denyself".equals(from)) {
		                return "redirect:/partylist/denyself?party_id=" + vo.getParty_id()+"&member_id="+vo.getMember_id();
				    } else {
				        return "redirect:/partylist/deny?party_id=" + vo.getParty_id()+"&member_id="+vo.getMember_id();
				    }
		        }
		        model.addAttribute("successMessage", "success");
		        return redirectToFormPage(from);
		    }
		
		 private String redirectToFormPage(String from) {
			    if ("denyself".equals(from)) {
			        return "partylist/denyself";
			    } else {
			        return "partylist/deny";
			    }
			}	

		 @GetMapping("/partylist/selectOne")
		 public String selectOne(int party_id, Model model, RedirectAttributes redirectAttributes) {
		     log.info("party_list_selectOne...");

		     // party_id 검증
		     if (party_id <= 0) {
		         redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 ID를 입력해 주세요.");
		         return "redirect:/party/selectAll";
		     }

		     PartyVO vo = new PartyVO();
		     List<MemberVO> listmember = new ArrayList<>();
		     List<MemberVO> listqueue = new ArrayList<>();

		     try {
		         vo.setParty_id(party_id);
		         vo = pservice.selectOne(vo);
		         
		         if (vo == null) {
		             redirectAttributes.addFlashAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다.");
		             return "redirect:/party/selectAll";
		         }
		         
		         // 파티원 및 대기자 목록 가져오기
		         List<PartyListVO> list = plservice.searchList("party_id", Integer.toString(party_id));
		         log.info("list{}", list);

		         for (PartyListVO vo2 : list) {
		             try {
		                 MemberVO vo3 = new MemberVO();
		                 vo3.setMember_id(vo2.getMember_id());
		                 vo3 = mservice.member_selectOneByMember_id(vo3);
		                 log.info("vo3{}", vo3);

		                 if (vo2.getParty_join()) {
		                     listmember.add(vo3);
		                 } else {
		                     listqueue.add(vo3);
		                 }
		             } catch (Exception e) {
		                 log.error("파티원 정보를 가져오는 중 오류 발생: {}", e.getMessage());
		             }
		         }
		     } catch (Exception e) {
		         log.error("데이터베이스 오류 발생: {}", e.getMessage());
		         redirectAttributes.addFlashAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
		         return "redirect:/party/selectAll";
		     }

		     log.info("vo{}", vo);
		     log.info("listmember{}", listmember);
		     log.info("listqueue{}", listqueue);
		     
		     model.addAttribute("vo", vo);
		     model.addAttribute("listmember", listmember);
		     model.addAttribute("listqueue", listqueue);
		     
		     return "partylist/selectOne";
		 }


		 @GetMapping("partylist/myparty")
		 public String myparty(Model model, RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "1") int cpage,
		                       @RequestParam(defaultValue = "10") int pageBlock) {
		     log.info("myparty...");

		     // 세션에서 사용자 ID 가져오기
		     String userId = (String) session.getAttribute("user_id");
		     if (userId == null || userId.trim().isEmpty()) {
		         redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다. 로그인 후 다시 시도해 주세요.");
		         return "redirect:/member/login"; // 로그인 페이지로 리다이렉트
		     }

		     MemberVO vo = new MemberVO();
		     vo.setId(userId);

		     try {
		         vo = mservice.member_selectOne(vo);
		         if (vo == null) {
		             redirectAttributes.addFlashAttribute("errorMessage", "사용자 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
		             return "redirect:/main"; // 메인 페이지로 리다이렉트
		         }

		         int member_id = vo.getMember_id(); // 현재 로그인 유저의 멤버 아이디
		         log.info("member_id: {}", member_id);

		         // 파티 목록 가져오기
		         List<MyPartyVO> list = plservice.searchMyParty(Integer.toString(member_id), cpage, pageBlock);
		         log.info("list: {}", list);

		         if (list == null || list.isEmpty()) {
		             model.addAttribute("errorMessage", "등록된 파티가 없습니다.");
		         }

		         model.addAttribute("list", list);
		         model.addAttribute("vo1", vo);

		         // 총 행수 가져오기
		         int total_rows = plservice.getTotalPartyListRows(Integer.toString(member_id));
		        
			        log.info("total_rows:{}", total_rows);
			        int totalPageCount;

			        if (total_rows == 0) {
			            totalPageCount = 1;
			        } else if (total_rows % pageBlock == 0) {
			            totalPageCount = total_rows / pageBlock;
			        } else {
			            totalPageCount = total_rows / pageBlock + 1;
			        }
			        		
			        log.info("totalPageCount:{}", totalPageCount);
			        
			        if (cpage < 1) {
			            cpage = 1;
			        } else if (cpage > totalPageCount) {
			            cpage = totalPageCount;
			        }

		         log.info("totalPageCount: {}", totalPageCount);

		         model.addAttribute("totalPageCount", totalPageCount);
		         model.addAttribute("currentPage", cpage);

		     } catch (Exception e) {
		         log.error("데이터베이스 오류 발생: {}", e.getMessage());
		         redirectAttributes.addFlashAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
		         return "redirect:/main"; // 메인 페이지로 리다이렉트
		     }

		     return "partylist/myparty";
		 }

}
