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
    public String insertOK(ReportBoardVO vo) {
        log.info("report_insertOK...");
        int result = rbService.insertOK(vo);
        log.info("result: {}", result);
        return "redirect:/reportboard/selectAll"; // 신고 게시판 목록으로 리다이렉트
    }

    @GetMapping("/reportboard/update")
    public String update(ReportBoardVO vo, Model model) {
        log.info("report_update...");
        ReportBoardVO vo2 = rbService.selectOne(vo);
        log.info("vo2: {}", vo2);
        model.addAttribute("vo2", vo2);
        return "reportboard/update"; // 신고 게시판 update 페이지
    }

    @PostMapping("/reportboard/updateOK")
    public String updateOK(ReportBoardVO vo) {
        log.info("report_updateOK...");
        int result = rbService.updateOK(vo);
        log.info("result: {}", result);
        return "redirect:/reportboard/selectAll"; // 신고 게시판 목록으로 리다이렉트
    }

    @GetMapping("/reportboard/delete")
    public String delete(ReportBoardVO vo, Model model) {
        log.info("report_delete...");
        ReportBoardVO vo2 = rbService.selectOne(vo);
        model.addAttribute("vo2", vo2);
        return "reportboard/delete"; // 신고 게시판 delete 페이지
    }

    @PostMapping("/reportboard/deleteOK")
    public String deleteOK(ReportBoardVO vo) {
        log.info("report_deleteOK...");
        int result = rbService.deleteOK(vo);
        log.info("result: {}", result);
        return "redirect:/reportboard/selectAll"; // 신고 게시판 목록으로 리다이렉트
    }

    @GetMapping("/reportboard/selectOne")
    public String selectOne(ReportBoardVO vo, Model model) {
        log.info("report_selectOne...");
        ReportBoardVO vo2 = rbService.selectOne(vo);
        List<ReportBoardCommentVO> list= rbcService.searchListByReportBoardId(Integer.toString(vo2.getReport_board_id()));
        log.info("vo2: {}", vo2);
        model.addAttribute("vo2", vo2);
        model.addAttribute("list", list);
        return "reportboard/selectOne"; // 신고 게시판 상세 페이지
    }

    @GetMapping("/reportboard/selectAll")
    public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
                            @RequestParam(defaultValue = "20") int pageBlock) {
        log.info("report_selectAll...");
        
        List<ReportBoardVO> list = rbService.selectAllPageBlock(cpage, pageBlock);
        String user_id=(String) session.getAttribute("user_id");
        int total_rows = rbService.getTotalRows();
        log.info("total_rows: {}", total_rows);
        log.info("user_id: {}", user_id);
        
        int totalPageCount = 0;

        // 총행카운트와 페이지블럭을 나눌때의 알고리즘
        if (total_rows / pageBlock == 0) {
            totalPageCount = 1;
        } else if (total_rows % pageBlock == 0) {
            totalPageCount = total_rows / pageBlock;
        } else {
            totalPageCount = total_rows / pageBlock + 1;
        }
        log.info("totalPageCount: {}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("currentPage", cpage);
        model.addAttribute("list", list);
        return "reportboard/selectAll"; // 신고 게시판 목록 페이지
    }

    @GetMapping("/reportboard/searchList")
    public String searchList(Model model, String searchKey, String searchWord,
                             @RequestParam(defaultValue = "1") int cpage,
                             @RequestParam(defaultValue = "20") int pageBlock) {
        log.info("report_searchList...");
        log.info("searchKey: {}", searchKey);
        log.info("searchWord: {}", searchWord);
        
        List<ReportBoardVO> list = rbService.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
        log.info("list: {}", list);
        
        int total_rows = rbService.getSearchTotalRows(searchKey, searchWord);
        log.info("total_rows: {}", total_rows);
        
        int totalPageCount = 0;

        // 총행카운트와 페이지블럭을 나눌때의 알고리즘
        if (total_rows / pageBlock == 0) {
            totalPageCount = 1;
        } else if (total_rows % pageBlock == 0) {
            totalPageCount = total_rows / pageBlock;
        } else {
            totalPageCount = total_rows / pageBlock + 1;
        }
        log.info("totalPageCount: {}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("currentPage", cpage);
        model.addAttribute("list", list);
        return "reportboard/selectAll"; // 신고 게시판 목록 페이지
    }
}
