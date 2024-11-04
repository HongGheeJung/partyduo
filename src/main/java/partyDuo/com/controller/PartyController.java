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

import dev.spiralmoon.maplestory.api.dto.character.CharacterBasicDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.NoticeVO;
import partyDuo.com.model.PartyBoardVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.CharacterService;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyBoardService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;

@Slf4j
@Controller
public class PartyController {
	
	@Autowired
	CharacterService cservice;
	
	@Autowired
	HttpSession session;

	@Autowired
	PartyService pservice;

	@Autowired
	PartyListService plservice;
	
	@Autowired
	PartyBoardService pbservice;

	@Autowired
	MemberService mservice;

	@GetMapping("/party/insert")
	public String insert(Model model) {
		log.info("party_insert...");
		
		 String user_character = (String) session.getAttribute("user_character");
		if (user_character == null || user_character.trim().isEmpty()){
			model.addAttribute("errorMessage", "로그인을 먼저 해주세요.");
			return "main";}

		return "party/insert";
	}

	@PostMapping("/party/insertOK")
	public String insertOK( Model model,PartyVO vo,RedirectAttributes redirectAttributes) {
		log.info("party_insertOK...");
		log.info("vo:{}", vo);
		
		
	    if (vo.getParty_name() == null || vo.getParty_name().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 이름은 필수 입력 항목입니다.");
	        return "redirect:/party/insert";  // 다시 입력 페이지로 리다이렉트
	    }
	    
//		String character_name = (String)session.getAttribute("user_character");
//		String ocid=cservice.foundOcidByName(character_name);
//		log.info("ocid: {}", ocid);
//		//캐릭터 기본정보
//		CharacterBasicDTO basicDTO=cservice.character_basic(ocid);
//		//캐릭터 스탯 정보
////		
//		String world=basicDTO.getWorldName();
//		log.info("world: {}", world);
//		vo.setParty_world(world); 
	    
	    vo.setParty_world("스카니아"); // 디폴트값 추후 삭제
	    
	    if (vo.getParty_world() == null || vo.getParty_world().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "월드 정보를 불러오는 중 오류가 발생했습니다.");
	        return "redirect:/party/insert";  // 다시 입력 페이지로 리다이렉트
	    }
	    
	    try {
	        int result = pservice.insertOK(vo);
	        log.info("result:{}", result);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 생성 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/party/insert";  // 에러 메시지를 포함하여 다시 입력 페이지로 리다이렉트
	    }
	    
	    try {
	        PartyListVO vo2 = new PartyListVO();
	        MemberVO vo3 = new MemberVO();
	        vo3.setId((String) session.getAttribute("user_id"));
	        vo3 = mservice.member_selectOne(vo3);
	        int member_id = vo3.getMember_id();
	        vo = pservice.selectOnePM(vo);
	        vo2.setParty_id(vo.getParty_id());
	        vo2.setMember_id(member_id);
	        vo2.setParty_join(true);

	        int result2 = plservice.insertOKPartyMaster(vo2);
	        log.info("result2:{}", result2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 생성 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/party/insert";
	    }
	    redirectAttributes.addFlashAttribute("successMessage", "success");
	    return "redirect:/party/insert";
	}

	@GetMapping("/party/update")
	public String update(PartyVO vo, RedirectAttributes redirectAttributes, Model model) {
	    log.info("party_update...");
	    
	    
	    
	    // 파티 ID가 유효하지 않은 경우
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	        return "redirect:/partylist/myparty"; // 마이파티 페이지로 리다이렉트
	    }
	    
	    PartyVO vo2 = null;
	    
	    try {
	        vo2 = pservice.selectOne(vo);
	        
	        String user_character = (String) session.getAttribute("user_character");
		    if (user_character == null || !user_character.equals(vo2.getParty_master())) {
		    	redirectAttributes.addFlashAttribute("errorMessage", "로그인 정보를 찾을 수 없습니다. 다시 로그인해주세요.");
		        return "redirect:/partylist/myparty";  // 작성자가 유효하지 않을 경우 다시 상세 페이지로 이동
		    }
		    
	        log.info("vo: {}", vo2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 정보 업데이트 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partylist/myparty";
	    }
	    
	    // 파티 정보가 유효한 경우 모델에 추가
	    model.addAttribute("vo2", vo2);
	    
	    return "party/update";
	}

	@GetMapping("/party/delegate")
	public String delegate(PartyVO vo, RedirectAttributes redirectAttributes, Model model) {
	    log.info("party_delegate...");
	    
	    // 로그인 여부 체크
	    
	    String user_character = (String) session.getAttribute("user_character");
	    if (user_character == null || user_character.trim().isEmpty()) {
	    	log.info("3");
	    	redirectAttributes.addFlashAttribute("errorMessage", "로그인을 먼저 해주세요.");
	        return "redirect:/main";
	    }
	    
	    // 유효한 파티 정보 확인
	    if (vo == null || vo.getParty_id() == 0) {
	    	log.info("1");
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        return "redirect:/partylist/myparty";
	    }

	    PartyVO vo2;
	    try {
	        vo2 = pservice.selectOne(vo);
	        if (vo2 == null) {
	        	log.info("2");
	            redirectAttributes.addFlashAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	            return "redirect:/partylist/myparty";
	        
	        }
    	    
	    } catch (Exception e) {
	    	log.info("5");
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partylist/myparty";
	    }

	    // 파티장 여부 체크
	    
	    

	    List<MemberVO> listmember = new ArrayList<>();
	    try {
	        List<PartyListVO> list = plservice.searchList("party_id", Integer.toString(vo.getParty_id()));
	        for (PartyListVO vo3 : list) {
	            if (vo3.getParty_join()) {
	                try {
	                    MemberVO vo4 = new MemberVO();
	                    vo4.setMember_id(vo3.getMember_id());
	                    vo4 = mservice.member_selectOneByMember_id(vo4);
	                    if (vo4 != null) {
	                        log.info("vo3{}", vo4);
	                        listmember.add(vo4);
	                    } else {
	                        log.warn("해당 멤버 정보를 찾을 수 없습니다. member_id: {}", vo3.getMember_id());
	                    }
	                } catch (Exception e) {
	                    log.error("멤버 정보 조회 오류 발생: {}", e.getMessage());
	                }
	            }
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 멤버 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partylist/myparty";
	    }
	    
	    log.info("vo: {}", vo2);
	    model.addAttribute("vo2", vo2);
	    model.addAttribute("listmember", listmember);
	    
	    return "party/delegate";
	}

	@PostMapping("/party/updateOK")
	public String updateOK(PartyVO vo, RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "") String from) {
	    log.info("party_updateOK...");

	    // VO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        return redirectToFormPage(from,vo);
	    }

	    if (vo.getParty_name() == null || vo.getParty_name().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 이름은 필수 입력 항목입니다.");
	        return redirectToFormPage(from,vo);
	    }

	    if (vo.getParty_master() == null || vo.getParty_master().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 마스터는 필수 입력 항목입니다.");
	        return redirectToFormPage(from,vo);
	    }
	    
	    PartyBoardVO vo2 = new PartyBoardVO();
	    try {
	        int result = pservice.updateOK(vo);
	        log.info("result:{}", result);
	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	            return redirectToFormPage(from,vo);
	        }
	        vo2.setParty_id(vo.getParty_id());
	        vo2.setParty_board_writer(vo.getParty_master()); 
	        int result1 = pbservice.masterUpdate(vo2);
	        log.info("result1:{}", result1);
	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	            return redirectToFormPage(from,vo);
	        }
	        
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return redirectToFormPage(from,vo);
	    }
	    log.info(from);
	    redirectAttributes.addFlashAttribute("successMessage", "success");
	    // 성공적으로 수정된 경우 리다이렉트
	    return redirectToFormPage(from,vo);
	}
	
	private String redirectToFormPage(String from,PartyVO vo) {
	    if ("delegate".equals(from)) {
	        return "redirect:/party/delegate?party_id="+vo.getParty_id();
	    } else {
	        return "redirect:/party/update?party_id="+vo.getParty_id();
	    }
	}

	@GetMapping("/party/delete")
	public String delete(PartyVO vo, RedirectAttributes redirectAttributes,Model model) {
log.info("party_update...");
	    
	    
	    
	    // 파티 ID가 유효하지 않은 경우
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	        return "redirect:/partylist/myparty"; // 마이파티 페이지로 리다이렉트
	    }
	    
	    PartyVO vo2 = null;
	    
	    try {
	        vo2 = pservice.selectOne(vo);
	     // 로그인 체크 및 파티 마스터 여부 확인
			String user_character = (String) session.getAttribute("user_character");	
			if (user_character == null || !user_character.equals(vo2.getParty_master())) {
		        redirectAttributes.addFlashAttribute("errorMessage", "로그인을 먼저 해주세요.");
		        return "redirect:/main"; // 로그인 페이지로 리다이렉트
		    }
			
	        log.info("vo: {}", vo2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 정보 업데이트 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partylist/myparty";
	    }
	    
	    
	    // 파티 정보가 유효한 경우 모델에 추가
	    model.addAttribute("vo2", vo2);
	    
	    return "party/delete";
	}

	@PostMapping("/party/deleteOK")
	public String deleteOK(PartyVO vo, RedirectAttributes redirectAttributes,Model model) {
	    log.info("party_deleteOK...");

	    // VO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        return "redirect:/party/delete?party_id="+vo.getParty_id(); // 유효한 파티 정보가 없는 경우, 삭제 페이지로 돌아감
	    }

	    try {
	        int result = pservice.deleteOK(vo);
	        log.info("result:{}", result);

	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 삭제에 실패했습니다. 다시 시도해 주세요.");
	            return "redirect:/party/delete?party_id="+vo.getParty_id(); // 삭제 실패 시 삭제 페이지로 돌아감
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/party/delete?party_id="+vo.getParty_id(); // 데이터베이스 오류 발생 시 삭제 페이지로 돌아감
	    }
	    model.addAttribute("successMessage", "success");
	    model.addAttribute("vo2", vo);
	    // 삭제 성공 시 리다이렉트
	    return "party/delete";
	}

	@GetMapping("/party/selectOne")
	public String selectOne(PartyVO vo, Model model) {
	    log.info("party_selectOne...");
	    
	    // PartyVO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        log.error("유효하지 않은 파티 정보 요청");
	        model.addAttribute("errorMessage", "유효하지 않은 파티 정보입니다. 다시 시도해 주세요.");
	        return "party/selectAll";  // 잘못된 요청일 경우 전체 목록 페이지로 이동
	    }

	    PartyVO vo2 = null;
	    try {
	        vo2 = pservice.selectOne(vo);
	        if (vo2 == null) {
	            log.error("파티 정보를 찾을 수 없습니다. party_id: {}", vo.getParty_id());
	            model.addAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	            return "party/selectAll";  // 파티 정보가 없을 경우 전체 목록 페이지로 이동
	        }
	        log.info("vo2:{}", vo2);
	        model.addAttribute("vo2", vo2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "party/selectAll";  // 오류 발생 시 전체 목록 페이지로 이동
	    }

	    return "party/selectOne";
	}

	@GetMapping("/admin/party/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
	        @RequestParam(defaultValue = "20") int pageBlock,RedirectAttributes redirectAttributes) {
	    log.info("/party_selectAll");

	    try {
	        // 페이지 요청이 1보다 작을 경우 기본값으로 설정
	    	int total_rows = pservice.getTotalRows();
	        int totalPageCount = (total_rows + pageBlock - 1) / pageBlock; // 총 페이지 수 계산

	        // cpage가 1보다 작거나 totalPageCount보다 크면 범위 내로 조정
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
	        List<PartyVO> list = pservice.selectAll(cpage, pageBlock);
	        log.info("list: {}", list);
	        model.addAttribute("list", list);

	        if (list == null || list.isEmpty()) {
	            model.addAttribute("errorMessage", "등록된 공지사항이 없습니다.");
	        }
	        
	        String admin_name = (String) session.getAttribute("admin_name");
		    if (admin_name == null ) {
		    	redirectAttributes.addFlashAttribute("errorMessage", "관리자 정보를 찾을 수 없습니다. 다시 로그인해주세요.");
		        return "redirect:/main";  // 작성자가 유효하지 않을 경우 다시 상세 페이지로 이동
		    }
		    
	        model.addAttribute("totalPageCount", totalPageCount);
	        model.addAttribute("currentPage", cpage);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/main"; // 오류 발생 시 메인 페이지로 리다이렉트
	    }
	    return "admin/party/selectAll";
	}

	@GetMapping("/admin/party/searchList")
	public String searchList(Model model, @RequestParam(defaultValue = "party_master") String searchKey,
	                         @RequestParam(defaultValue = "ad") String searchWord,@RequestParam(defaultValue = "1") int cpage,
	             	        @RequestParam(defaultValue = "20") int pageBlock,RedirectAttributes redirectAttributes) {
	    log.info("party_searchList...");
	    log.info("searchKey: {}", searchKey);
	    log.info("searchWord: {}", searchWord);

	    try {
	        // 페이지 요청이 1보다 작을 경우 기본값으로 설정
	    	int total_rows = pservice.getSearchTotalRows(searchKey, searchWord);
	        int totalPageCount = (total_rows + pageBlock - 1) / pageBlock; // 총 페이지 수 계산

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
	        // 검색어 유효성 검사
	        if (searchKey == null || searchKey.trim().isEmpty()) {
	            redirectAttributes.addFlashAttribute("errorMessage", "검색 키를 입력해 주세요.");
	            return "redirect:/admin/party/selectAll";
	        }

	        if (searchWord == null || searchWord.trim().isEmpty()) {
	            redirectAttributes.addFlashAttribute("errorMessage", "검색어를 입력해 주세요.");
	            return "redirect:/admin/party/selectAll";
	        }

	        List<PartyVO> list = pservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
	        log.info("list: {}", list);

	        if (list == null || list.isEmpty()) {
	            model.addAttribute("errorMessage", "검색된 공지사항이 없습니다.");
	        }
	        String admin_name = (String) session.getAttribute("admin_name");
		    if (admin_name == null ) {
		    	redirectAttributes.addFlashAttribute("errorMessage", "관리자 정보를 찾을 수 없습니다. 다시 로그인해주세요.");
		        return "redirect:/admin/party/selectAll";  // 작성자가 유효하지 않을 경우 다시 상세 페이지로 이동
		    }
	        
	        model.addAttribute("totalPageCount", totalPageCount);
	        model.addAttribute("currentPage", cpage);
	        model.addAttribute("list", list);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 검색 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/admin/party/selectAll"; // 오류 발생 시 공지사항 목록으로 리다이렉트
	    }

	    return "admin/party/selectAll";
	}
	
	@GetMapping("/admin/party/update")
	public String adminUpdate(PartyVO vo, RedirectAttributes redirectAttributes, Model model) {
	    log.info("party_admin_update...");
	    
	    
	    
	    // 파티 ID가 유효하지 않은 경우
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	        return "redirect:/admin/party/selectAll"; // 마이파티 페이지로 리다이렉트
	    }
	    
	    PartyVO vo2 = null;
	    
	    try {
	        vo2 = pservice.selectOne(vo);
	        
	        String admin_name = (String) session.getAttribute("admin_name");
		    if (admin_name == null ) {
		    	redirectAttributes.addFlashAttribute("errorMessage", "관리자 정보를 찾을 수 없습니다. 다시 로그인해주세요.");
		        return "redirect:/admin/party/selectAll";  // 작성자가 유효하지 않을 경우 다시 상세 페이지로 이동
		    }
		    
	        log.info("vo: {}", vo2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 정보 업데이트 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/admin/party/selectAll";
	    }
	    
	    // 파티 정보가 유효한 경우 모델에 추가
	    model.addAttribute("vo2", vo2);
	    
	    return "admin/party/update";
	}
	
	@PostMapping("/admin/party/updateOK")
	public String adminUpdateOK(PartyVO vo, RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "") String from) {
	    log.info("party_updateOK...");

	    // VO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        return  "redirect:/admin/party/update?party_id="+vo.getParty_id();
	    }

	    if (vo.getParty_name() == null || vo.getParty_name().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 이름은 필수 입력 항목입니다.");
	        return  "redirect:/admin/party/update?party_id="+vo.getParty_id();
	    }

	    if (vo.getParty_master() == null || vo.getParty_master().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 마스터는 필수 입력 항목입니다.");
	        return  "redirect:/admin/party/update?party_id="+vo.getParty_id(); 
	    }
	    

	    try {
	        int result = pservice.updateOK(vo);
	        log.info("result:{}", result);
	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	            return  "redirect:/admin/party/update?party_id="+vo.getParty_id(); 
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return  "redirect:/admin/party/update?party_id="+vo.getParty_id(); 
	    }
	    log.info(from);
	    redirectAttributes.addFlashAttribute("successMessage", "success");
	    // 성공적으로 수정된 경우 리다이렉트
	    return  "redirect:/admin/party/update?party_id="+vo.getParty_id(); 
	}
	
	@GetMapping("/admin/party/delete")
	public String adminDelete(PartyVO vo, RedirectAttributes redirectAttributes,Model model) {
log.info("party_update...");
	    
	    
	    
	    // 파티 ID가 유효하지 않은 경우
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	        return "redirect:/admin/party/selectAll"; // 마이파티 페이지로 리다이렉트
	    }
	    
	    PartyVO vo2 = null;
	    
	    try {
	        vo2 = pservice.selectOne(vo);
	     // 로그인 체크 및 파티 마스터 여부 확인
	        String admin_name = (String) session.getAttribute("admin_name");
		    if (admin_name == null ) {
		    	redirectAttributes.addFlashAttribute("errorMessage", "관리자 정보를 찾을 수 없습니다. 다시 로그인해주세요.");
		        return "redirect:/admin/party/selectAll";  // 작성자가 유효하지 않을 경우 다시 상세 페이지로 이동
		    }
			
	        log.info("vo: {}", vo2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 정보 업데이트 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/admin/party/selectAll";
	    }
	    
	    
	    // 파티 정보가 유효한 경우 모델에 추가
	    model.addAttribute("vo2", vo2);
	    
	    return "admin/party/delete";
	}

	@PostMapping("/admin/party/deleteOK")
	public String adminDeleteOK(PartyVO vo, RedirectAttributes redirectAttributes,Model model) {
	    log.info("party_deleteOK...");

	    // VO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        return "redirect:/admin/party/delete?party_id="+vo.getParty_id(); // 유효한 파티 정보가 없는 경우, 삭제 페이지로 돌아감
	    }

	    try {
	        int result = pservice.deleteOK(vo);
	        log.info("result:{}", result);

	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "파티 삭제에 실패했습니다. 다시 시도해 주세요.");
	            return "redirect:/admin/party/delete?party_id="+vo.getParty_id(); // 삭제 실패 시 삭제 페이지로 돌아감
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "파티 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/admin/party/delete?party_id="+vo.getParty_id(); // 데이터베이스 오류 발생 시 삭제 페이지로 돌아감
	    }
	    model.addAttribute("successMessage", "success");
	    model.addAttribute("vo2", vo);
	    // 삭제 성공 시 리다이렉트
	    return "admin/party/delete";
	}
}
