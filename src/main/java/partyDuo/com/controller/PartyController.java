package partyDuo.com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.spiralmoon.maplestory.api.dto.character.CharacterBasicDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.CharacterService;
import partyDuo.com.service.MemberService;
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
	MemberService mservice;

	@GetMapping("/party/insert")
	public String insert() {
		log.info("party_insert...");
		return "party/insert";
	}

	@PostMapping("/party/insertOK")
	public String insertOK( Model model,PartyVO vo) {
		log.info("party_insertOK...");
		log.info("vo:{}", vo);
		
		
	    if (vo.getParty_name() == null || vo.getParty_name().trim().isEmpty()) {
	        model.addAttribute("errorMessage", "파티 이름은 필수 입력 항목입니다.");
	        return "party/insert";  // 다시 입력 페이지로 이동
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
	        model.addAttribute("errorMessage", "월드정보를 불러오는중 오류가 발생했습니다.");
	        return "party/insert";  // 다시 입력 페이지로 이동
	    }
	    
		try {
	        int result = pservice.insertOK(vo);
	        log.info("result:{}", result);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 생성 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "party/insert";  // 에러 메시지를 포함하여 다시 입력 페이지로 이동
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
	        model.addAttribute("errorMessage", "파티 생성 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "party/insert";
	    }

		return "redirect:/partylist/myparty";
	}

	@GetMapping("/party/update")
	public String update(PartyVO vo, Model model) {
	    log.info("party_update...");
	    
	    if (vo == null || vo.getParty_id() == 0) {
	        model.addAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	        return "party/myparty";  // 입력 페이지로 이동
	    }
	    
	    PartyVO vo2=null;
	    
	    try {
	         vo2 = pservice.selectOne(vo);
	        log.info("vo: {}", vo2);
	        model.addAttribute("vo2", vo2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 정보 업데이트 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "party/myparty";
	    }
	    
	    if (vo2 == null) {
	        model.addAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	        return "party/myparty";
	    }
	    
	    return "party/update";
	}

	@GetMapping("/party/delegate")
	public String delegate(PartyVO vo, Model model) {
		log.info("party_delegate...");
		 if (vo == null || vo.getParty_id() == 0) {
		        model.addAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
		        return "party/myparty";  // 입력 페이지로 이동
		    }

		    PartyVO vo2;
		    try {
		        vo2 = pservice.selectOne(vo);
		        if (vo2 == null) {
		            model.addAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
		            return "party/myparty";
		        }
		    } catch (Exception e) {
		        log.error("데이터베이스 오류 발생: {}", e.getMessage());
		        model.addAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
		        return "party/myparty";
		    }
		    
		    List<MemberVO> listmember = new ArrayList<>();
		    try {
		        List<PartyListVO> list = plservice.searchList("party_id", Integer.toString(vo.getParty_id()));
		        for (PartyListVO vo3 : list) {
		            if (vo3.getParty_join() == true) {
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
		        model.addAttribute("errorMessage", "파티 멤버 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
		        return "party/myparty";
		    }
		    
		log.info("{vo:{}", vo2);
		model.addAttribute("vo2", vo2);
		model.addAttribute("listmember", listmember);
		return "party/delegate";
	}

	@PostMapping("/party/updateOK")
	public String updateOK(PartyVO vo, Model model, @RequestParam(defaultValue = "") String from) {
	    log.info("party_updateOK...");

	    // VO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        if ("delegate".equals(from)) {
	            return "party/delegate";
	        } else {
	            return "party/update";
	        }
	    }

	    if (vo.getParty_name() == null || vo.getParty_name().trim().isEmpty()) {
	        model.addAttribute("errorMessage", "파티 이름은 필수 입력 항목입니다.");
	        if ("delegate".equals(from)) {
	            return "party/delegate";
	        } else {
	            return "party/update";
	        }
	    }

	    if (vo.getParty_master() == null || vo.getParty_master().trim().isEmpty()) {
	        model.addAttribute("errorMessage", "파티 마스터는 필수 입력 항목입니다.");
	        if ("delegate".equals(from)) {
	            return "party/delegate";
	        } else {
	            return "party/update";
	        }
	    }

	    try {
	        int result = pservice.updateOK(vo);
	        log.info("result:{}", result);
	        if (result == 0) {
	            model.addAttribute("errorMessage", "파티 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	            if ("delegate".equals(from)) {
	                return "party/delegate";
	            } else {
	                return "party/update";
	            }
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        if ("delegate".equals(from)) {
	            return "party/delegate";
	        } else {
	            return "party/update";
	        }
	    }

	    // 성공적으로 수정된 경우 리다이렉트
	    return "redirect:/party/selectOne?party_id=" + vo.getParty_id();
	}


	@GetMapping("/party/delete")
	public String delete(PartyVO vo, Model model) {
	    log.info("party_delete...");

	    // VO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        return "redirect:/party/myparty";  // 유효한 정보가 없으면 파티 목록으로 리다이렉트
	    }

	    try {
	        PartyVO vo2 = pservice.selectOne(vo);
	        if (vo2 == null) {
	            model.addAttribute("errorMessage", "해당 파티 정보를 찾을 수 없습니다.");
	            return "redirect:/party/myparty";  // 존재하지 않는 경우 파티 목록으로 리다이렉트
	        }
	        model.addAttribute("vo2", vo2);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/party/myparty";  // 데이터베이스 오류 발생 시 파티 목록으로 리다이렉트
	    }

	    return "party/delete";
	}

	@PostMapping("/party/deleteOK")
	public String deleteOK(PartyVO vo, Model model) {
	    log.info("party_deleteOK...");

	    // VO 필드 체크
	    if (vo == null || vo.getParty_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 정보를 입력해 주세요.");
	        return "party/delete";  // 유효한 파티 정보가 없는 경우, 삭제 페이지로 돌아감
	    }

	    try {
	        int result = pservice.deleteOK(vo);
	        log.info("result:{}", result);
	        
	        if (result == 0) {
	            model.addAttribute("errorMessage", "파티 삭제에 실패했습니다. 다시 시도해 주세요.");
	            return "party/delete";  // 삭제 실패 시 삭제 페이지로 돌아감
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "party/delete";  // 데이터베이스 오류 발생 시 삭제 페이지로 돌아감
	    }

	    // 삭제 성공 시 리다이렉트
	    return "redirect:/partylist/myparty";
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

	@GetMapping("/party/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
	        @RequestParam(defaultValue = "5") int pageBlock) {
	    log.info("/party_selectAll");

	    try {
	        // 페이지 요청이 1보다 작을 경우 기본값으로 설정
	        if (cpage < 1) {
	            log.warn("잘못된 페이지 요청: {}", cpage);
	            cpage = 1; // 기본값 설정
	        }

	        if (pageBlock < 1) {
	            log.warn("잘못된 페이지 블록 요청: {}", pageBlock);
	            pageBlock = 5; // 기본값 설정
	        }

	        List<PartyVO> list = pservice.selectAll(cpage, pageBlock);
	        log.info("list: {}", list);
	        model.addAttribute("list", list);

	        // 파티 정보가 없을 경우 사용자에게 알림 메시지를 전달
	        if (list == null || list.isEmpty()) {
	            model.addAttribute("errorMessage", "등록된 파티 정보가 없습니다.");
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "main"; // 오류 페이지로 이동
	    }

	    return "party/selectAll";
	}

	@GetMapping("/party/searchList")
	public String searchList(Model model, @RequestParam(defaultValue = "party_master") String searchKey,
	                         @RequestParam(defaultValue = "ad") String searchWord) {
	    log.info("party_searchList...");

	    // 유효성 검사
	    if (searchKey == null || searchKey.trim().isEmpty()) {
	        model.addAttribute("errorMessage", "검색 키워드가 유효하지 않습니다.");
	        return "party/selectAll";
	    }

	    if (searchWord == null || searchWord.trim().isEmpty()) {
	        model.addAttribute("errorMessage", "검색어가 비어 있습니다. 모든 파티를 조회합니다.");
	    }

	    try {
	        List<PartyVO> list = pservice.searchList(searchKey, searchWord);
	        log.info("list: {}", list);
	        
	        // 조회된 결과가 없는 경우
	        if (list == null || list.isEmpty()) {
	            model.addAttribute("errorMessage", "검색 결과가 없습니다.");
	        }

	        model.addAttribute("list", list);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "검색 도중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "party/selectAll"; // 오류 페이지로 이동
	    }

	    return "party/selectAll";
	}

}
