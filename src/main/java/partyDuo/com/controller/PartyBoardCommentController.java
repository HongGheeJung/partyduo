package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.PartyBoardCommentVO;
import partyDuo.com.service.PartyBoardCommentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class PartyBoardCommentController {

	@Autowired
	PartyBoardCommentService pbcservice;
	
	@Autowired
	HttpSession session;
	
	@PostMapping("/partyboardcomment/insertOK")
	public String insertOK(PartyBoardCommentVO vo, Model model) {
	    log.info("party_board_comment_insertOK...");

	    // 필수 필드 체크
	    if (vo == null || vo.getParty_board_id() == 0 || 
	        vo.getParty_board_comment_content() == null || vo.getParty_board_comment_content().trim().isEmpty() || 
	        vo.getParty_board_comment_writer() == null || vo.getParty_board_comment_writer().trim().isEmpty()) {
	        
	        model.addAttribute("errorMessage", "유효한 댓글 정보를 입력해 주세요.");
	        return "partyboard/selectAll";  // 유효하지 않은 정보가 있으면 다시 상세 페이지로 이동
	    }

	    // 세션에 저장된 유저 캐릭터 확인
	    String user_character = (String) session.getAttribute("user_character");
	    if (user_character == null || !user_character.equals(vo.getParty_board_comment_writer())) {
	        model.addAttribute("errorMessage", "댓글 작성자는 현재 로그인된 캐릭터와 일치해야 합니다.");
	        return "partyboard/selectAll";  // 작성자가 유효하지 않을 경우 다시 상세 페이지로 이동
	    }

	    try {
	        int result = pbcservice.insertOK(vo);
	        if (result == 0) {
	            model.addAttribute("errorMessage", "댓글 등록에 실패했습니다. 다시 시도해 주세요.");
	            return "partyboard/selectAll";  // 등록 실패 시 다시 상세 페이지로 이동
	        }
	    } catch (Exception e) {
	        log.error("댓글 등록 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "댓글 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll";  // 데이터베이스 오류 발생 시 다시 상세 페이지로 이동
	    }

	    // 댓글 등록 성공 시 리다이렉트
	    return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	}

	@GetMapping("/partyboardcomment/update")
	public String update(PartyBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
	    log.info("party_board_comment_update...");

	    // 필수 필드 체크
	    if (vo == null || vo.getParty_board_comment_id() == 0) {
	    	redirectAttributes.addFlashAttribute("errorMessage", "유효한 댓글 정보를 입력해 주세요.");
	        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	    }

	    PartyBoardCommentVO vo2 = null;
	    try {
	        vo2 = pbcservice.selectOne(vo);
	        if (vo2 == null) {
	        	redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글 정보를 찾을 수 없습니다. 다시 시도해 주세요.");
	            return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	        }
	        
	     // 작성자 검증 (세션의 유저 캐릭터와 작성자 비교)
		    String user_character = (String) session.getAttribute("user_character");
		    if (!user_character.equals(vo2.getParty_board_comment_writer()) ^ (String)session.getAttribute("admin_name") != null) {
		    	redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글을 수정할 권한이 없습니다.");
		    	log.error("댓글 정보 불러오는 중 오류 발생");
		    	return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
		    
		    }
		    
	    } catch (Exception e) {
	        log.error("댓글 정보 불러오는 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "댓글 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	    }

	    

	    model.addAttribute("vo2", vo2);
	    return "partyboardcomment/update";
	}

	@PostMapping("/partyboardcomment/updateOK")
	public String updateOK(PartyBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
	    log.info("party_board_comment_updateOK...");

	    // VO 필드 유효성 검증
	    if (vo == null || vo.getParty_board_comment_id() == 0) {
	    	redirectAttributes.addFlashAttribute("errorMessage", "유효한 댓글 정보를 입력해 주세요.");
	    	return "redirect:/partyboardcomment/update?party_board_comment_id="+vo.getParty_board_comment_id();
	    }

	    if (vo.getParty_board_comment_content() == null || vo.getParty_board_comment_content().trim().isEmpty()) {
	    	redirectAttributes.addFlashAttribute("errorMessage", "댓글 내용을 입력해 주세요.");
	    	return "redirect:/partyboardcomment/update?party_board_comment_id="+vo.getParty_board_comment_id();
	    }

	    // 작성자 검증 (세션의 유저 캐릭터와 작성자 비교)
	    String user_character = (String) session.getAttribute("user_character");
	    if (user_character == null ^ (String)session.getAttribute("admin_name") != null ) {
	    	redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글을 수정할 권한이 없습니다.");
	    	return "redirect:/partyboardcomment/update?party_board_comment_id="+vo.getParty_board_comment_id();
	    }

	    try {
	        int result = pbcservice.updateOK(vo);
	        if (result == 0) {
	        	redirectAttributes.addFlashAttribute("errorMessage", "댓글 수정에 실패했습니다. 다시 시도해 주세요.");
	        	return "redirect:/partyboardcomment/update?party_board_comment_id="+vo.getParty_board_comment_id();
	        }
	    } catch (Exception e) {
	        log.error("댓글 수정 중 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "댓글 수정 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partyboardcomment/update?party_board_comment_id="+vo.getParty_board_comment_id();
	    }
	    
	    redirectAttributes.addFlashAttribute("successMessage", "success");
	    // 성공적으로 수정된 경우 리다이렉트
	    return "redirect:/partyboardcomment/update?party_board_comment_id="+vo.getParty_board_comment_id();
	}

	@GetMapping("/partyboardcomment/delete")
	public String delete(PartyBoardCommentVO vo, Model model,RedirectAttributes redirectAttributes) {
	    log.info("partyboardcomment_delete...");

	    // 댓글 정보 조회
	    PartyBoardCommentVO vo2 = null;
	    try {
	        vo2 = pbcservice.selectOne(vo);
	        if (vo2 == null) {
	        	redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글 정보를 찾을 수 없습니다.");
	            return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	        }
	        
	        String user_character = (String) session.getAttribute("user_character");
		    if ( !user_character.equals(vo2.getParty_board_comment_writer()) ^ (String)session.getAttribute("admin_name") != null) {
		    	redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글을 삭제할 권한이 없습니다.");
		        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
		    }
		    
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "댓글 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	    }

	    // 작성자 검증 (세션의 유저 캐릭터와 작성자 비교)
	   

	    // 삭제 페이지로 이동하여 확인
	    model.addAttribute("vo2", vo2);
	    return "partyboardcomment/delete";
	}

	@PostMapping("/partyboardcomment/deleteOK")
	public String deleteOK(PartyBoardCommentVO vo,RedirectAttributes redirectAttributes,Model model) {
	    log.info("party_board_comment_deleteOK...");

	    // 댓글 ID와 게시판 ID 유효성 검사
	    if (vo == null || vo.getParty_board_comment_id() == 0 || vo.getParty_board_id() == 0) {
	    	redirectAttributes.addFlashAttribute("errorMessage", "유효한 댓글 정보를 입력해 주세요.");
	        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	    }

	    // 댓글 작성자 확인
	    PartyBoardCommentVO vo2 = null;
	    try {
	        vo2 = pbcservice.selectOne(vo);
	        if (vo2 == null) {
	        	redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글 정보를 찾을 수 없습니다.");
	            return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "댓글 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	    }

	    // 작성자 검증 (세션의 유저 캐릭터와 작성자 비교)
	    String sessionUserCharacter = (String) session.getAttribute("user_character");
	    if (!sessionUserCharacter.equals(vo2.getParty_board_comment_writer()) ^ (String)session.getAttribute("admin_name") != null) {
	    	redirectAttributes.addFlashAttribute("errorMessage", "해당 댓글을 삭제할 권한이 없습니다.");
	        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	    }

	    // 댓글 삭제 처리
	    try {
	        int result = pbcservice.deleteOK(vo);
	        if (result == 0) {
	        	redirectAttributes.addFlashAttribute("errorMessage", "댓글 삭제에 실패했습니다. 다시 시도해 주세요.");
	            return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	        }
	    } catch (Exception e) {
	        log.error("데이터베이스 오류 발생: {}", e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", "댓글 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	    }
	    model.addAttribute("successMessage", "success");
	    model.addAttribute("vo2", vo);
		
	    return "partyboardcomment/delete";
	}

}
