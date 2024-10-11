package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.PartyService;

@Slf4j
@Controller
public class PartyController {
	@Autowired
	PartyService pservice;
	
	@GetMapping("/party/insert")
	public String insert() {
		log.info("party_insert...");
		return "party/insert";			
	}
	
	@GetMapping("/party/update")
	public String update() {
		log.info("party_update...");
		return "party/update";			
	}
	@GetMapping("/party/delete")
	public String delete() {
		log.info("party_delete...");
		return "party/delete";			
	}
	@GetMapping("/party/selectOne")
	public String selectOne() {
		log.info("party_selectOne...");
		pservice.selectOne();
		return "party/selectOne";			
	}
	
	@GetMapping("/party/searchList")
	public String searchList() {
		log.info("party_searchList...");
		pservice.searchList();
		return "party/searchList";			
	}
	@PostMapping("/party/insertOK")
	public String insertOK() {
		log.info("party_insertOK...");
		int result=pservice.insertOK();
		return "redirect:/party/searchList";			
	}
	@PostMapping("/party/updateOK")
	public String updateOK() {
		log.info("party_updateOK...");
		int result=pservice.updateOK();
		return "redirect:/party/searchList";			
	}
	@PostMapping("/party/deleteOK")
	public String deleteOK() {
		log.info("party_deleteOK...");
		int result=pservice.deleteOK();
		return "redirect:/party/searchList";			
	}
}
