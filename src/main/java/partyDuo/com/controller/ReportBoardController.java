package partyDuo.com.controller;

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
import partyDuo.com.model.ReportBoardCommentVO;
import partyDuo.com.model.ReportBoardVO; // 신고 게시판 VO
import partyDuo.com.service.ReportBoardCommentService;
import partyDuo.com.service.ReportBoardService; // 신고 게시판 서비스

@Slf4j
@Controller
public class ReportBoardController {

    @Autowired
    ReportBoardService rbService; // 신고 게시판 서비스
    
    @Autowired
    ReportBoardCommentService rbcService; // 신고 게시판 서비스
    
    @Autowired
    HttpSession session;

    @GetMapping("/reportboard/insert")
    public String insert() {
        log.info("report_insert...");
        return "reportboard/insert"; // 신고 게시판 insert 페이지
    }

    @PostMapping("/reportboard/insertOK")
    public String insertOK(ReportBoardVO vo, RedirectAttributes redirectAttributes) {
        log.info("report_insertOK...");

        // 필수 필드 유효성 검사
        if (vo.getReport_board_title() == null || vo.getReport_board_title().trim().isEmpty()) {
        	redirectAttributes.addFlashAttribute("errorMessage", "제목은 필수 입력 항목입니다.");
            return "redirect:/reportboard/insert";
        }

        if (vo.getReport_board_content() == null || vo.getReport_board_content().trim().isEmpty()) {
        	redirectAttributes.addFlashAttribute("errorMessage", "내용은 필수 입력 항목입니다.");
            return "redirect:/reportboard/insert";
        }

        if (vo.getReport_board_writer() == null || vo.getReport_board_writer().trim().isEmpty()) {
        	redirectAttributes.addFlashAttribute("errorMessage", "작성자는 필수 입력 항목입니다.");
            return "redirect:/reportboard/insert";
        }

        try {
            int result = rbService.insertOK(vo);
            log.info("result: {}", result);
        } catch (Exception e) {
            log.error("신고 게시판 등록 중 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "신고 게시판 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "redirect:/reportboard/insert";
        }
        redirectAttributes.addFlashAttribute("successMessage", "success");
        return "redirect:/reportboard/insert"; // 신고 게시판 목록으로 리다이렉트
    }

    @GetMapping("/reportboard/update")
    public String update(ReportBoardVO vo, Model model) {
        log.info("report_update...");

        // 게시물 조회
        ReportBoardVO vo2 = rbService.selectOne(vo);
        if (vo2 == null) {
            model.addAttribute("errorMessage", "해당 게시물을 찾을 수 없습니다.");
            return "reportboard/selectAll";
        }

        // 작성자 확인 (세션의 사용자와 작성자 비교)
        String user_character = (String) session.getAttribute("user_character");
        if (!vo2.getReport_board_writer().equals(user_character)) {
            model.addAttribute("errorMessage", "작성자만 수정할 수 있습니다.");
            return "reportboard/selectAll";
        }

        model.addAttribute("vo2", vo2);
        return "reportboard/update"; // 신고 게시판 update 페이지
    }

    @PostMapping("/reportboard/updateOK")
    public String updateOK(ReportBoardVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("report_updateOK...");

        // 필수 필드 유효성 검사
        if (vo.getReport_board_title() == null || vo.getReport_board_title().trim().isEmpty()) {
        	redirectAttributes.addFlashAttribute("errorMessage", "제목은 필수 입력 항목입니다.");
            return "redirect:/reportboard/update";
        }

        if (vo.getReport_board_content() == null || vo.getReport_board_content().trim().isEmpty()) {
        	redirectAttributes.addFlashAttribute("errorMessage", "내용은 필수 입력 항목입니다.");
            return "redirect:/reportboard/update";
        }

        try {
            int result = rbService.updateOK(vo);
            log.info("result: {}", result);
            if (result == 0) {
            	redirectAttributes.addFlashAttribute("errorMessage", "신고 게시물 수정에 실패했습니다.");
                return "redirect:/reportboard/update";
            }
        } catch (Exception e) {
            log.error("신고 게시물 수정 중 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "신고 게시물 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "redirect:/reportboard/update";
        }
        redirectAttributes.addFlashAttribute("successMessage", "success");
        return "redirect:/reportboard/update"; // 신고 게시판 목록으로 리다이렉트
    }
    
    @GetMapping("/reportboard/delete")
    public String delete(ReportBoardVO vo, Model model) {
        log.info("report_delete...");

        ReportBoardVO vo2 = rbService.selectOne(vo);
        if (vo2 == null) {
            model.addAttribute("errorMessage", "해당 게시물을 찾을 수 없습니다.");
            return "redirect:/reportboard/selectAll";
        }

        // 작성자 확인 (세션의 사용자와 작성자 비교)
        String user_character = (String) session.getAttribute("user_character");
        if (!vo2.getReport_board_writer().equals(user_character)) {
            model.addAttribute("errorMessage", "작성자만 삭제할 수 있습니다.");
            return "redirect:/reportboard/selectAll";
        }

        model.addAttribute("vo2", vo2);
        return "reportboard/delete"; // 신고 게시판 delete 페이지
    }

    @PostMapping("/reportboard/deleteOK")
    public String deleteOK(ReportBoardVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("report_deleteOK...");

        try {
            int result = rbService.deleteOK(vo);
            log.info("result: {}", result);
            if (result == 0) {
            	redirectAttributes.addFlashAttribute("errorMessage", "게시물 삭제에 실패했습니다.");
                return "redirect:/reportboard/delete";
            }
        } catch (Exception e) {
            log.error("게시물 삭제 중 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "게시물 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "redirect:/reportboard/delete";
        }
        redirectAttributes.addFlashAttribute("successMessage", "success");
        return "redirect:/reportboard/delete"; // 신고 게시판 목록으로 리다이렉트
    }

    @GetMapping("/reportboard/selectOne")
    public String selectOne(ReportBoardVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("report_selectOne...");

        // 유효성 검사: report_board_id 체크
        if (vo == null || vo.getReport_board_id() == 0) {
        	redirectAttributes.addFlashAttribute("errorMessage", "유효한 신고 게시물을 찾을 수 없습니다. 다시 시도해 주세요.");
            return "redirect:/reportboard/selectAll";
        }

        ReportBoardVO vo2;
        try {
            vo2 = rbService.selectOne(vo);
            if (vo2 == null) {
            	redirectAttributes.addFlashAttribute("errorMessage", "해당 신고 게시물을 찾을 수 없습니다.");
                return "redirect:/reportboard/selectAll";
            }
        } catch (Exception e) {
            log.error("신고 게시물 조회 중 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "신고 게시물을 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "redirect:/reportboard/selectAll";
        }

        // 댓글 목록 조회
        List<ReportBoardCommentVO> list;
        try {
            list = rbcService.searchListByReportBoardId(Integer.toString(vo2.getReport_board_id()));
        } catch (Exception e) {
            log.error("댓글 목록 조회 중 오류 발생: {}", e.getMessage());
            model.addAttribute("errorMessage", "댓글 목록을 불러오는 중 오류가 발생했습니다.");
            list = null; // 오류 발생 시 댓글 목록을 null로 설정
        }

        log.info("vo2: {}", vo2);
        model.addAttribute("vo2", vo2);
        model.addAttribute("list", list);

        return "reportboard/selectOne"; // 신고 게시판 상세 페이지
    }

    @GetMapping("/reportboard/selectAll")
    public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
                            @RequestParam(defaultValue = "20") int pageBlock) {
        log.info("report_selectAll...");

        try {
            // 페이지 요청이 1보다 작을 경우 기본값으로 설정
            if (cpage < 1) {
                log.warn("잘못된 페이지 요청: {}", cpage);
                cpage = 1; // 기본값 설정
            }

            if (pageBlock < 1) {
                log.warn("잘못된 페이지 블록 요청: {}", pageBlock);
                pageBlock = 20; // 기본값 설정
            }

            // 신고 게시물 목록 조회
            List<ReportBoardVO> list = rbService.selectAllPageBlock(cpage, pageBlock);
            if (list == null || list.isEmpty()) {
                model.addAttribute("errorMessage", "등록된 신고 게시물이 없습니다.");
            }
            log.info("list: {}", list);
            model.addAttribute("list", list);

            // 총 행 개수 조회 및 총 페이지 수 계산
            int total_rows = rbService.getTotalRows();
            log.info("total_rows: {}", total_rows);

            int totalPageCount;
            if (total_rows == 0) {
                totalPageCount = 1;
            } else if (total_rows % pageBlock == 0) {
                totalPageCount = total_rows / pageBlock;
            } else {
                totalPageCount = (total_rows / pageBlock) + 1;
            }
            log.info("totalPageCount: {}", totalPageCount);

            model.addAttribute("totalPageCount", totalPageCount);
            model.addAttribute("currentPage", cpage);
            
        } catch (Exception e) {
            log.error("데이터베이스 오류 발생: {}", e.getMessage());
            model.addAttribute("errorMessage", "신고 게시물을 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
            return "main"; // 오류 발생 시 메인 페이지로 이동
        }

        return "reportboard/selectAll"; // 신고 게시판 목록 페이지
    }


    @GetMapping("/reportboard/searchList")
    public String searchList(Model model, String searchKey, String searchWord,
                             @RequestParam(defaultValue = "1") int cpage,
                             @RequestParam(defaultValue = "20") int pageBlock) {
        log.info("report_searchList...");
        log.info("searchKey: {}", searchKey);
        log.info("searchWord: {}", searchWord);

        try {
            // 페이지 요청이 1보다 작을 경우 기본값으로 설정
            if (cpage < 1) {
                log.warn("잘못된 페이지 요청: {}", cpage);
                cpage = 1; // 기본값 설정
            }

            if (pageBlock < 1) {
                log.warn("잘못된 페이지 블록 요청: {}", pageBlock);
                pageBlock = 20; // 기본값 설정
            }

            // 검색 키워드가 유효하지 않은 경우
            if (searchKey == null || searchKey.trim().isEmpty() || searchWord == null || searchWord.trim().isEmpty()) {
                log.warn("유효하지 않은 검색 키워드: searchKey={}, searchWord={}", searchKey, searchWord);
                model.addAttribute("errorMessage", "유효한 검색 키워드를 입력해 주세요.");
                return "reportboard/selectAll";
            }

            // 검색 목록 조회
            List<ReportBoardVO> list = rbService.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
            if (list == null || list.isEmpty()) {
                model.addAttribute("errorMessage", "검색된 결과가 없습니다.");
            }
            log.info("list: {}", list);
            model.addAttribute("list", list);

            // 총 검색 결과 수 조회 및 총 페이지 수 계산
            int total_rows = rbService.getSearchTotalRows(searchKey, searchWord);
            log.info("total_rows: {}", total_rows);

            int totalPageCount;
            if (total_rows == 0) {
                totalPageCount = 1;
            } else if (total_rows % pageBlock == 0) {
                totalPageCount = total_rows / pageBlock;
            } else {
                totalPageCount = (total_rows / pageBlock) + 1;
            }
            log.info("totalPageCount: {}", totalPageCount);

            model.addAttribute("totalPageCount", totalPageCount);
            model.addAttribute("currentPage", cpage);
            
        } catch (Exception e) {
            log.error("데이터베이스 오류 발생: {}", e.getMessage());
            model.addAttribute("errorMessage", "검색 과정에서 오류가 발생했습니다. 다시 시도해 주세요.");
            return "main"; // 오류 발생 시 메인 페이지로 이동
        }

        return "reportboard/selectAll"; // 신고 게시판 목록 페이지
    }
}
