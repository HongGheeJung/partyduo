package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.ReportBoardCommentVO; // 수정된 VO
import partyDuo.com.service.ReportBoardCommentService; // 수정된 서비스

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ReportBoardCommentController {

    @Autowired
    ReportBoardCommentService rbcService; // 수정된 서비스

    @PostMapping("/reportboardcomment/insertOK")
    public String insertOK(ReportBoardCommentVO vo) {
        log.info("report_board_comment_insertOK...");
        int result = rbcService.insertOK(vo);
        return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id(); // 신고 게시판 상세 페이지로 리다이렉트
    }

    @GetMapping("/reportboardcomment/update")
    public String update(ReportBoardCommentVO vo, Model model) {
        log.info("report_board_comment_update...");
        ReportBoardCommentVO vo2 = rbcService.selectOne(vo);
        log.info("vo: {}", vo2);
        model.addAttribute("vo2", vo2);
        return "reportboardcomment/update"; // 수정 페이지
    }

    @PostMapping("/reportboardcomment/updateOK")
    public String updateOK(ReportBoardCommentVO vo) {
        log.info("report_board_comment_updateOK...");
        log.info("vo: {}", vo);
        int result = rbcService.updateOK(vo);
        return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id(); // 신고 게시판 상세 페이지로 리다이렉트
    }

    @GetMapping("/reportboardcomment/delete")
    public String delete(ReportBoardCommentVO vo, Model model) {
        log.info("reportboardcomment_delete...");
        ReportBoardCommentVO vo2 = rbcService.selectOne(vo);
        model.addAttribute("vo2", vo2);
        return "reportboardcomment/delete"; // 삭제 확인 페이지
    }

    @PostMapping("/reportboardcomment/deleteOK")
    public String deleteOK(ReportBoardCommentVO vo) {
        log.info("report_board_comment_deleteOK...");
        int result = rbcService.deleteOK(vo);
        return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id(); // 신고 게시판 상세 페이지로 리다이렉트
    }

}
