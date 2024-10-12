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
	
	@GetMapping("/partyboardcomment/insert")
	public String insert() {
		log.info("party_board_comment_insert...");
		return "partyboardcomment/insert";			
	}
	
	@GetMapping("/partyboardcomment/update")
	public String update() {
		log.info("party_board_comment_update...");
		return "partyboardcomment/update";			
	}
	@GetMapping("/partyboardcomment/delete")
	public String delete() {
		log.info("partyboardcomment_delete...");
		return "partyboardcomment/delete";			
	}
	@GetMapping("/partyboardcomment/selectOne")
	public String selectOne() {
		log.info("partyboardcomment_selectOne...");
		pbcservice.selectOne();
		return "partyboardcomment/selectOne";			
	}
	
	@GetMapping("/partyboardcomment/searchList")
	public String searchList() {
		log.info("partyboardcomment_searchList...");
		pbcservice.searchList();
		return "partyboardcomment/searchList";			
	}
	@PostMapping("/partyboardcomment/insertOK")
	public String insertOK()  {
		log.info("party_board_comment_insertOK...");
		int result = pbcservice.insertOK();
		return "redirect:/party_board_comment/selectOne";			
	}
	@PostMapping("/partyboardcomment/updateOK")
	public String updateOK()  {
		log.info("party_board_comment_updateOK...");
		int result = pbcservice.updateOK();
		return "redirect:/party_board_comment/selectOne";			
	} 
	@PostMapping("/partyboardcomment/deleteOK")
	public String deleteOK() {
		log.info("party_board_comment_deleteOK...");
		int result = pbcservice.deleteOK();
		return "redirect:/party_board_comment/selectOne";			
	}
}
