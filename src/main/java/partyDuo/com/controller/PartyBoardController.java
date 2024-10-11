package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.PartyBoardService;

@Slf4j
@Controller
public class PartyBoardController {
	
	@Autowired
	PartyBoardService pbservice;
	@GetMapping("/party_board/insert")
	public String insert() {
		log.info("party_board_insert...");
		return "partyboard/insert";			
	}
	
	@GetMapping("/party_board/update")
	public String update() {
		log.info("party_board_update...");
		return "partyboard/update";			
	}
	@GetMapping("/party_board/delete")
	public String delete() {
		log.info("party_board_delete...");
		return "partyboard/delete";			
	}
	@GetMapping("/party_board/selectOne")
	public String selectOne() {
		log.info("party_board_selectOne...");
		pbservice.selectOne();
		return "partyboard/selectOne";			
	}
	@GetMapping("/party_board/selectAll")
	public String selectAll() {
		log.info("party_board_selectAll...");
		pbservice.selectAll();
		return "partyboard/selectAll";			
	}
	@GetMapping("/party_board/searchList")
	public String searchList() {
		log.info("party_board_searchList...");
		pbservice.searchList();
		return "partyboard/searchList";			
	}
	@PostMapping("/party_board/insertOK")
	public String insertOK() {
		log.info("party_board_insertOK...");
		int result = pbservice.insertOK();
		return "redirect:/party_board/selectAll";			
	}
	@PostMapping("/party_board/updateOK")
	public String updateOK() {
		log.info("party_board_updateOK...");
		int result = pbservice.updateOK();
		return "redirect:/party_board/selectAll";			
	}
	@PostMapping("/party_board/deleteOK")
	public String deleteOK() {
		log.info("party_board_deleteOK...");
		int result = pbservice.deleteOK();
		return "redirect:/party_board/selectAll";			
	}
}
