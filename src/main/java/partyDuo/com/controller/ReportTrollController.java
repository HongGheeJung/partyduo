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
import partyDuo.com.model.ReportTrollVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;
import partyDuo.com.service.ReportTrollService;

@Slf4j
@Controller
public class ReportTrollController {
	@Autowired
	ReportTrollService rtservice;
	
	@GetMapping("/reporttroll/insert")
	public String insert() {
		log.info("report_troll_insert...");
		
		return "reporttroll/insert";
	}
	
	 @PostMapping("/reporttroll/insertOK")
	    public String insertOK(ReportTrollVO vo, RedirectAttributes redirectAttributes) {
	        log.info("report_troll_insertOK...");
	        try {
	            int result = rtservice.insertOK(vo);
	            if (result == 0) {
	                redirectAttributes.addFlashAttribute("errorMessage", "트롤 신고 등록에 실패했습니다. 다시 시도해 주세요.");
	                return "redirect:/reporttroll/insert";
	            }
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "트롤 신고 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/reporttroll/insert";
	        }
	        redirectAttributes.addFlashAttribute("successMessage", "success");
	        return "redirect:/reporttroll/insert";
	    }

	    @GetMapping("/reporttroll/update")
	    public String update(ReportTrollVO vo, Model model, RedirectAttributes redirectAttributes) {
	        log.info("report_troll_update...");

	        // report_troll_id 검증
	        if (vo == null || vo.getReport_troll_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 신고 정보를 입력해 주세요.");
	            return "redirect:/reporttroll/selectAll";
	        }

	        try {
	            ReportTrollVO vo2 = rtservice.selectOne(vo);
	            if (vo2 == null) {
	                redirectAttributes.addFlashAttribute("errorMessage", "해당 신고 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	                return "redirect:/reporttroll/selectAll";
	            }
	            model.addAttribute("vo2", vo2);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "신고 정보 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/reporttroll/selectAll";
	        }

	        return "reporttroll/update";
	    }

	    @PostMapping("/reporttroll/updateOK")
	    public String updateOK(ReportTrollVO vo, RedirectAttributes redirectAttributes) {
	        log.info("report_troll_updateOK...");

	        // report_troll_id 검증
	        if (vo == null || vo.getReport_troll_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 신고 정보를 입력해 주세요.");
	            return "redirect:/reporttroll/update?report_troll_id=" + vo.getReport_troll_id();
	        }

	        try {
	            int result = rtservice.updateOK(vo);
	            if (result == 0) {
	                redirectAttributes.addFlashAttribute("errorMessage", "신고 정보 수정에 실패했습니다. 다시 시도해 주세요.");
	                return "redirect:/reporttroll/update?report_troll_id=" + vo.getReport_troll_id();
	            }
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "신고 정보 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/reporttroll/update?report_troll_id=" + vo.getReport_troll_id();
	        }
	        redirectAttributes.addFlashAttribute("successMessage", "success");
	        return "redirect:/reporttroll/update?report_troll_id=" + vo.getReport_troll_id();
	    }

	    @GetMapping("/reporttroll/delete")
	    public String delete(ReportTrollVO vo, Model model, RedirectAttributes redirectAttributes) {
	        log.info("report_troll_delete...");

	        // report_troll_id 검증
	        if (vo == null || vo.getReport_troll_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 신고 정보를 입력해 주세요.");
	            return "redirect:/reporttroll/selectAll";
	        }

	        try {
	            ReportTrollVO vo2 = rtservice.selectOne(vo);
	            if (vo2 == null) {
	                redirectAttributes.addFlashAttribute("errorMessage", "해당 신고 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	                return "redirect:/reporttroll/selectAll";
	            }
	            model.addAttribute("vo2", vo2);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "신고 정보 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/reporttroll/selectAll";
	        }

	        return "reporttroll/delete";
	    }

	    @PostMapping("/reporttroll/deleteOK")
	    public String deleteOK(ReportTrollVO vo, RedirectAttributes redirectAttributes,Model model) {
	        log.info("report_troll_deleteOK...");

	        // report_troll_id 검증
	        if (vo == null || vo.getReport_troll_id() == 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "유효한 신고 정보를 입력해 주세요.");
	            return "redirect:/reporttroll/delete?report_troll_id=" + vo.getReport_troll_id();
	        }

	        try {
	            int result = rtservice.deleteOK(vo);
	            if (result == 0) {
	                redirectAttributes.addFlashAttribute("errorMessage", "신고 정보 삭제에 실패했습니다. 다시 시도해 주세요.");
	                return "redirect:/reporttroll/delete?report_troll_id=" + vo.getReport_troll_id();
	            }
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "신고 정보 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/reporttroll/delete?report_troll_id=" + vo.getReport_troll_id();
	        }
	        model.addAttribute("vo2", vo);
	        model.addAttribute("successMessage", "success");
	        return "reporttroll/delete";	  
	}
	
	    @GetMapping("/reporttroll/selectOne")
	    public String selectOne(ReportTrollVO vo, Model model,RedirectAttributes redirectAttributes) {
	        log.info("report_troll_selectOne...");
	        try {
	            ReportTrollVO vo2 = rtservice.selectOne(vo);
	            if (vo2 == null) {
	            	redirectAttributes.addFlashAttribute("errorMessage", "해당 신고 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	                return "redirect:/reporttroll/selectAll";
	            }
	            model.addAttribute("vo2", vo2);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "신고 정보 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/reporttroll/selectAll";
	        }
	        return "reporttroll/selectOne";
	    }

	    @GetMapping("/reporttroll/selectAll")
	    public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
	                            @RequestParam(defaultValue = "20") int pageBlock) {
	        log.info("report_troll_selectAll...");
	        try {
	        	int total_rows = rtservice.getTotalRows();
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

	            List<ReportTrollVO> list = rtservice.selectAllPageBlock(cpage, pageBlock);
	            log.info("list: {}", list);

	            if (list == null || list.isEmpty()) {
	                model.addAttribute("errorMessage", "등록된 신고 정보가 없습니다.");
	            }

	           
	            log.info("totalPageCount: {}", totalPageCount);

	            model.addAttribute("totalPageCount", totalPageCount);
	            model.addAttribute("currentPage", cpage);
	            model.addAttribute("list", list);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            model.addAttribute("errorMessage", "신고 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "main";
	        }
	        return "reporttroll/selectAll";
	    }

	    @GetMapping("/reporttroll/searchList")
	    public String searchList(Model model, String searchKey, String searchWord,
	                             @RequestParam(defaultValue = "1") int cpage,
	                             @RequestParam(defaultValue = "20") int pageBlock,
	                             RedirectAttributes redirectAttributes) {
	        log.info("report_troll_searchList...");
	        try {
	        	int total_rows = rtservice.getSearchTotalRows(searchKey, searchWord);
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

	            List<ReportTrollVO> list = rtservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
	            log.info("list: {}", list);

	            if (list == null || list.isEmpty()) {
	                model.addAttribute("errorMessage", "검색 결과가 없습니다.");
	            }

	           
	            log.info("totalPageCount: {}", totalPageCount);

	            model.addAttribute("totalPageCount", totalPageCount);
	            model.addAttribute("currentPage", cpage);
	            model.addAttribute("list", list);
	        } catch (Exception e) {
	            log.error("데이터베이스 오류 발생: {}", e.getMessage());
	            redirectAttributes.addFlashAttribute("errorMessage", "검색 중 오류가 발생했습니다. 다시 시도해 주세요.");
	            return "redirect:/reporttroll/selectAll";
	        }
	        return "reporttroll/selectAll";
	    }
	}
