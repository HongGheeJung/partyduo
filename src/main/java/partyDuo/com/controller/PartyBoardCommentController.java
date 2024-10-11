package partyDuo.com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.PartyBoardCommentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class PartyBoardCommentController {
	
	@Autowired
	PartyBoardCommentService pbcservice;
	
	@GetMapping("/party_board_comment/insert")
	public String insert() {
		log.info("party_board_comment_insert...");
		return "partyboardcomment/insert";			
	}
	
	@GetMapping("/party_board_comment/update")
	public String update() {
		log.info("party_board_comment_update...");
		return "partyboardcomment/update";			
	}
	@GetMapping("/party_board_comment/delete")
	public String delete() {
		log.info("party_board_comment_delete...");
		return "partyboardcomment/delete";			
	}
	@GetMapping("/party_board_comment/selectOne")
	public String selectOne() {
		log.info("party_board_comment_selectOne...");
		pbcservice.selectOne();
		return "partyboardcomment/selectOne";			
	}
	
	@GetMapping("/party_board_comment/searchList")
	public String searchList() {
		log.info("party_board_comment_searchList...");
		pbcservice.searchList();
		return "partyboardcomment/searchList";			
	}
	@PostMapping("/party_board_comment/insertOK")
	public String insertOK()throws IllegalStateException, IOException {
		log.info("party_board_comment_insertOK...");
		int result = pbcservice.insertOK();
		return "redirect:/party_board_comment/selectOne";			
	}
	@PostMapping("/party_board_comment/updateOK")
	public String updateOK()throws IllegalStateException, IOException {
		log.info("party_board_comment_updateOK...");
		int result = pbcservice.updateOK();
		return "redirect:/party_board_comment/selectOne";			
	}
	@PostMapping("/party_board_comment/deleteOK")
	public String deleteOK()throws IllegalStateException, IOException {
		log.info("party_board_comment_deleteOK...");
		int result = pbcservice.deleteOK();
		return "redirect:/party_board_comment/selectOne";			
	}
}
