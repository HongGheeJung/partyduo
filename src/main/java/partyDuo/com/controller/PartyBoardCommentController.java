package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.PartyBoardCommentVO;
import partyDuo.com.service.PartyBoardCommentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class PartyBoardCommentController {

	@Autowired
	PartyBoardCommentService pbcservice;

	@PostMapping("/partyboardcomment/insertOK")
	public String insertOK(PartyBoardCommentVO vo) {
		log.info("party_board_comment_insertOK...");
		int result = pbcservice.insertOK(vo);
		return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	}

	@GetMapping("/partyboardcomment/update")
	public String update(PartyBoardCommentVO vo, Model model) {
		log.info("party_board_comment_update...");
		PartyBoardCommentVO vo2 = pbcservice.selectOne(vo);
		log.info("vo:{}", vo2);
		model.addAttribute("vo2", vo2);
		return "partyboardcomment/update";
	}

	@PostMapping("/partyboardcomment/updateOK")
	public String updateOK(PartyBoardCommentVO vo) {
		log.info("party_board_comment_updateOK...");
		log.info("vo:{}", vo);
		int result = pbcservice.updateOK(vo);
		return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	}

	@GetMapping("/partyboardcomment/delete")
	public String delete(PartyBoardCommentVO vo, Model model) {
		log.info("partyboardcomment_delete...");
		PartyBoardCommentVO vo2 = pbcservice.selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "partyboardcomment/delete";
	}

	@PostMapping("/partyboardcomment/deleteOK")
	public String deleteOK(PartyBoardCommentVO vo) {
		log.info("party_board_comment_deleteOK...");
		int result = pbcservice.deleteOK(vo);
		return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	}

}
