package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.PartyListService;

@Slf4j
@Controller
public class PartyListController {
	@Autowired
	PartyListService plservice;
	
	@GetMapping("/partylist/insert")
	public String insert() {
		log.info("party_list_insert...");
		return "partylist/insert";			
	}
	
	@GetMapping("/partylist/update")
	public String update() {
		log.info("party_list_update...");
		return "partylist/update";			
	}
	@GetMapping("/partylist/delete")
	public String delete() {
		log.info("party_list_delete...");
		return "partylist/delete";			
	}
	@GetMapping("/partylist/selectOne")
	public String selectOne() {
		log.info("party_list_selectOne...");
		plservice.selectOne();
		return "partylist/selectOne";			
	}
	
	@GetMapping("/partylist/searchList")
	public String searchList() {
		log.info("party_list_searchList...");
		plservice.searchList();
		return "partylist/searchList";			
	}
	@PostMapping("/partylist/insertOK")
	public String insertOK() {
		log.info("party_list_insertOK...");
		int result=plservice.insertOK();
		return "redirect:/party_list/searchList";			
	}
	@PostMapping("/partylist/updateOK")
	public String updateOK() {
		log.info("party_list_updateOK...");
		int result=plservice.updateOK();
		return "redirect:/party_list/searchList";			
	}
	@PostMapping("/partylist/deleteOK")
	public String deleteOK() {
		log.info("party_list_deleteOK...");
		int result=plservice.deleteOK();
		return "redirect:/party_list/searchList";			
	}
}
