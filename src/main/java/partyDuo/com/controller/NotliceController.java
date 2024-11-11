package partyDuo.com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.model.NoticeVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.NoticeService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;
import partyDuo.com.service.ReportTrollService;

@Slf4j
@Controller
public class NotliceController {
	@Autowired
	NoticeService nservice;
	
	@Autowired
	HttpSession session;
	@GetMapping("/notice/insert")
	public String insert(Model model) {
		log.info("notice_insert...");
		String admin_name=(String) session.getAttribute("admin_name");
		if(admin_name == null || admin_name.trim().isEmpty()) {
			model.addAttribute("errorMessage", "관리자가 아닙니다.");
		    return "main";
		}
		return "notice/insert";
	}
	
	@PostMapping("/notice/insertOK")
	public String insertOK(NoticeVO vo, RedirectAttributes redirectAttributes) {
	    log.info("notice_insertOK...");

	    // 필수 필드 검증
	    if (vo == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 정보를 입력해 주세요.");
	        return "redirect:/notice/insert";  // 다시 입력 페이지로 리다이렉트
	    }

	    if (vo.getNotice_title() == null || vo.getNotice_title().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 제목은 필수 입력 항목입니다.");
	        return "redirect:/notice/insert";
	    }

	    if (vo.getNotice_content() == null || vo.getNotice_content().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 내용은 필수 입력 항목입니다.");
	        return "redirect:/notice/insert";
	    }

	    if (vo.getNotice_writer() == null || vo.getNotice_writer().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "작성자는 필수 입력 항목입니다.");
	        return "redirect:/notice/insert";
	    }

	    try {
	        int result = nservice.insertOK(vo);
	        log.info("result:{}", result);

	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 등록에 실패했습니다. 다시 시도해 주세요.");
	            return "redirect:/notice/insert";
	        }
	    } catch (Exception e) {
	        log.error("공지사항 등록 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/notice/insert";
	    }

	    redirectAttributes.addFlashAttribute("successMessage", "success");
	    return "redirect:/notice/insert";  // 공지사항 목록으로 리다이렉트
	}
	
	@GetMapping("/notice/update")
	public String update(NoticeVO vo, Model model, RedirectAttributes redirectAttributes) {
	    log.info("notice_update...");

	    // 필수 필드 검증: id가 0이거나 유효하지 않은 경우 오류 처리
	    if (vo == null || vo.getNotice_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 공지사항 정보를 입력해 주세요.");
	        return "redirect:/notice/selectAll"; // 공지사항 목록 페이지로 리다이렉트
	    }
	    
	    NoticeVO vo2 = null;
	    
	    String admin_name=(String) session.getAttribute("admin_name");
        if(admin_name == null || admin_name.trim().isEmpty()) {
			model.addAttribute("errorMessage", "관리자가 아닙니다.");
		    return "main";
        }
		try {
	    	
	        vo2 = nservice.selectOne(vo);
	        if (vo2 == null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "해당 공지사항 정보를 찾을 수 없습니다.");
	            return "redirect:/notice/selectAll"; // 존재하지 않는 경우 목록 페이지로 리다이렉트
	            
	    		}
	       
	    } catch (Exception e) {
	        log.error("공지사항 정보 조회 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 정보 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/notice/selectAll";
	    }

	    log.info("vo2: {}", vo2);
	    model.addAttribute("vo2", vo2);
	    return "notice/update";
	}

	@PostMapping("/notice/updateOK")
	public String updateOK(NoticeVO vo, RedirectAttributes redirectAttributes,Model model) {
	    log.info("notice_updateOK...");

	    // 필수 필드 검증
	    if (vo == null || vo.getNotice_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 공지사항 정보를 입력해 주세요.");
	        return "redirect:/notice/update?notice_id=" + vo.getNotice_id();
	    }
	    
	    if (vo.getNotice_title() == null || vo.getNotice_title().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 제목은 필수 입력 항목입니다.");
	        return "redirect:/notice/update?notice_id=" + vo.getNotice_id();
	    }
	    
	    if (vo.getNotice_content() == null || vo.getNotice_content().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 내용은 필수 입력 항목입니다.");
	        return "redirect:/notice/update?notice_id=" + vo.getNotice_id();
	    }

	    if (vo.getNotice_writer() == null || vo.getNotice_writer().trim().isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "작성자는 필수 입력 항목입니다.");
	        return "redirect:/notice/update?notice_id=" + vo.getNotice_id();
	    }

	    try {
	        int result = nservice.updateOK(vo);
	        log.info("result: {}", result);

	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	            return "redirect:/notice/update?notice_id=" + vo.getNotice_id();
	        }
	    } catch (Exception e) {
	        log.error("공지사항 수정 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/notice/update?notice_id=" + vo.getNotice_id();
	    }

	    // 수정 성공 시 리다이렉트
	    redirectAttributes.addFlashAttribute("successMessage", "success");
	    
	    return "redirect:/notice/update?notice_id=" + vo.getNotice_id();
	}
	
	@GetMapping("/notice/delete")
	public String delete(NoticeVO vo, Model model, RedirectAttributes redirectAttributes) {
	    log.info("notice_delete...");

	    // 필수 필드 검증
	    if (vo == null || vo.getNotice_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 공지사항 정보를 입력해 주세요.");
	        return "redirect:/notice/selectAll"; // 공지사항 목록으로 리다이렉트
	    }
	    
	    String admin_name=(String) session.getAttribute("admin_name");
        if(admin_name == null || admin_name.trim().isEmpty()) {
			model.addAttribute("errorMessage", "관리자가 아닙니다.");
		    return "main";
        }   
	    try {
	        NoticeVO vo2 = nservice.selectOne(vo);
	        if (vo2 == null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "해당 공지사항을 찾을 수 없습니다. 다시 시도해 주세요.");
	            return "redirect:/notice/selectAll"; // 공지사항 목록으로 리다이렉트
	        }

	        model.addAttribute("vo2", vo2);
	    } catch (Exception e) {
	        log.error("공지사항 조회 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/notice/selectAll"; // 오류 발생 시 공지사항 목록으로 리다이렉트
	    }

	    return "notice/delete";
	}


	@PostMapping("/notice/deleteOK")
	public String deleteOK(NoticeVO vo, RedirectAttributes redirectAttributes,Model model) {
	    log.info("notice_deleteOK...");

	    // 필수 필드 검증
	    if (vo == null || vo.getNotice_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 공지사항 정보를 입력해 주세요.");
	        return "redirect:/notice/delete?notice_id=" + vo.getNotice_id(); // 공지사항 목록으로 리다이렉트
	    }

	    try {
	        int result = nservice.deleteOK(vo);
	        log.info("result: {}", result);

	        if (result == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "공지사항 삭제에 실패했습니다. 다시 시도해 주세요.");
	            return "redirect:/notice/delete?notice_id=" + vo.getNotice_id(); // 삭제 실패 시 목록으로 리다이렉트
	        }
	    } catch (Exception e) {
	        log.error("공지사항 삭제 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/notice/delete?notice_id=" + vo.getNotice_id(); // 오류 발생 시 목록으로 리다이렉트
	    }

	    model.addAttribute("successMessage", "success");
	    model.addAttribute("vo2", vo);
	    return "notice/delete"; // 삭제 성공 시 목록으로 리다이렉트
	}
	
	@GetMapping("/notice/selectOne")
	public String selectOne(NoticeVO vo, Model model, RedirectAttributes redirectAttributes) {
	    log.info("notice_selectOne...");

	    // 필수 필드 검증
	    if (vo == null || vo.getNotice_id() == 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효한 공지사항 정보를 입력해 주세요.");
	        return "redirect:/notice/selectAll"; // 공지사항 목록으로 리다이렉트
	    }

	    try {
	        NoticeVO vo2 = nservice.selectOne(vo);
	        if (vo2 == null) {
	            redirectAttributes.addFlashAttribute("errorMessage", "해당 공지사항 정보를 찾을 수 없습니다.");
	            return "redirect:/notice/selectAll"; // 공지사항이 존재하지 않는 경우 목록으로 리다이렉트
	        }
	        log.info("vo2: {}", vo2);
	        model.addAttribute("vo2", vo2);
	    } catch (Exception e) {
	        log.error("공지사항 조회 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/notice/selectAll"; // 오류 발생 시 목록으로 리다이렉트
	    }

	    return "notice/selectOne"; // 공지사항 상세 페이지로 이동
	}
	
	@GetMapping("/notice/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
	                        @RequestParam(defaultValue = "20") int pageBlock, RedirectAttributes redirectAttributes) {
	    log.info("notice_selectAll...");

	    try {
	        // 페이지 요청이 1보다 작을 경우 기본값으로 설정
	    	int total_rows = nservice.getTotalRows();
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
	        List<NoticeVO> list = nservice.selectAllPageBlock(cpage, pageBlock);
	      
	        model.addAttribute("list", list);

	        if (list == null || list.isEmpty()) {
	            model.addAttribute("errorMessage", "등록된 공지사항이 없습니다.");
	        }

	        model.addAttribute("totalPageCount", totalPageCount);
	        model.addAttribute("currentPage", cpage);
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/main"; // 오류 발생 시 메인 페이지로 리다이렉트
	    }

	    return "notice/selectAll";
	}
	
	@GetMapping("/notice/searchList")
	public String searchList(Model model, String searchKey, String searchWord, 
	                         @RequestParam(defaultValue = "1") int cpage,
	                         @RequestParam(defaultValue = "20") int pageBlock,
	                         RedirectAttributes redirectAttributes) {
	    log.info("notice_searchList...");
	    log.info("searchKey: {}", searchKey);
	    log.info("searchWord: {}", searchWord);

	    try {
	        // 페이지 요청이 1보다 작을 경우 기본값으로 설정
	    	int total_rows = nservice.getSearchTotalRows(searchKey, searchWord);
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
	            return "redirect:/notice/selectAll";
	        }

	        if (searchWord == null || searchWord.trim().isEmpty()) {
	            redirectAttributes.addFlashAttribute("errorMessage", "검색어를 입력해 주세요.");
	            return "redirect:/notice/selectAll";
	        }

	        List<NoticeVO> list = nservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
	      

	        if (list == null || list.isEmpty()) {
	            model.addAttribute("errorMessage", "검색된 공지사항이 없습니다.");
	        }

	        
	        model.addAttribute("totalPageCount", totalPageCount);
	        model.addAttribute("currentPage", cpage);
	        model.addAttribute("list", list);
	        model.addAttribute("searchKey", searchKey);
	        model.addAttribute("searchWord", searchWord);
	       
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "공지사항 검색 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/notice/selectAll"; // 오류 발생 시 공지사항 목록으로 리다이렉트
	    }

	    return "notice/selectAll"; // 검색 결과 페이지
	}
}
