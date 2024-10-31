package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.ReportBoardCommentVO; // 수정된 VO
import partyDuo.com.service.ReportBoardCommentService; // 수정된 서비스

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class ReportBoardCommentController {

    @Autowired
    ReportBoardCommentService rbcService; // 수정된 서비스
    
    @Autowired
    HttpSession session;
    
    @PostMapping("/reportboardcomment/insertOK")
    public String insertOK(ReportBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("report_board_comment_insertOK...");

        // 세션에서 admin_name 확인
        String admin_name = (String) session.getAttribute("admin_name");
        if (admin_name == null) {
            log.warn("권한 없음: 관리자 이름이 없습니다.");
            redirectAttributes.addFlashAttribute("errorMessage", "댓글을 작성하려면 관리자 권한이 필요합니다.");
            return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id();
        }

        try {
            int result = rbcService.insertOK(vo);
            log.info("result: {}", result);
        } catch (Exception e) {
            log.error("댓글 삽입 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "댓글 작성 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }

        return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id();
    }

    @GetMapping("/reportboardcomment/update")
    public String update(ReportBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("report_board_comment_update...");

        ReportBoardCommentVO vo2 = rbcService.selectOne(vo);
        if (vo2 == null) {
            log.warn("존재하지 않는 댓글 정보");
            redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글 정보를 찾을 수 없습니다.");
            return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id();
        }

        // 세션에서 admin_name 확인 후 작성자와 일치 여부 확인
        String admin_name = (String) session.getAttribute("admin_name");
        if (admin_name == null ) {
            log.warn("권한 없음: 관리자 이름이 일치하지 않음");
            redirectAttributes.addFlashAttribute("errorMessage", "댓글을 수정할 권한이 없습니다.");
            return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id();
        }

        model.addAttribute("vo2", vo2);
        return "reportboardcomment/update";
    }

    @PostMapping("/reportboardcomment/updateOK")
    public String updateOK(ReportBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("report_board_comment_updateOK...");
        log.info("vo: {}", vo);

        // 세션에서 admin_name 확인 후 작성자와 일치 여부 확인
        String admin_name = (String) session.getAttribute("admin_name");
        if (admin_name == null ) {
            log.warn("권한 없음: 관리자 이름이 일치하지 않음");
            redirectAttributes.addFlashAttribute("errorMessage", "댓글을 수정할 권한이 없습니다.");
            return "redirect:/reportboardcomment/update?report_board_comment_id="+vo.getReport_board_comment_id();
        }

        try {
            int result = rbcService.updateOK(vo);
            log.info("result: {}", result);
        } catch (Exception e) {
            log.error("댓글 수정 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "댓글 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
        redirectAttributes.addFlashAttribute("successMessage", "success");
        return "redirect:/reportboardcomment/update?report_board_comment_id="+vo.getReport_board_comment_id();
    }

    @GetMapping("/reportboardcomment/delete")
    public String delete(ReportBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("reportboardcomment_delete...");

        ReportBoardCommentVO vo2 = rbcService.selectOne(vo);
        if (vo2 == null) {
            log.warn("존재하지 않는 댓글 정보");
            redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글 정보를 찾을 수 없습니다.");
            return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id();
        }

        // 세션에서 admin_name 확인 후 작성자와 일치 여부 확인
        String admin_name = (String) session.getAttribute("admin_name");
        if (admin_name == null ) {
            log.warn("권한 없음: 관리자 이름이 일치하지 않음");
            redirectAttributes.addFlashAttribute("errorMessage", "댓글을 삭제할 권한이 없습니다.");
            return "redirect:/reportboard/selectOne?report_board_id=" + vo.getReport_board_id();
        }

        model.addAttribute("vo2", vo2);
        return "reportboardcomment/delete";
    }

    @PostMapping("/reportboardcomment/deleteOK")
    public String deleteOK(ReportBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
        log.info("report_board_comment_deleteOK...");

        // 세션에서 admin_name 확인 후 작성자와 일치 여부 확인
        String admin_name = (String) session.getAttribute("admin_name");
        if (admin_name == null) {
            log.warn("권한 없음: 관리자 이름이 일치하지 않음");
            redirectAttributes.addFlashAttribute("errorMessage", "댓글을 삭제할 권한이 없습니다.");
            return "redirect:/reportboardcomment/delete?report_board_comment_id="+vo.getReport_board_comment_id();
        }

        try {
            int result = rbcService.deleteOK(vo);
            log.info("result: {}", result);
        } catch (Exception e) {
            log.error("댓글 삭제 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "댓글 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
        model.addAttribute("successMessage", "success");
        model.addAttribute("vo2",vo);
        return "reportboardcomment/delete";
    }

}
