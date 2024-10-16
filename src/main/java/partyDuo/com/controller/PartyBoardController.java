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
import partyDuo.com.model.PartyBoardVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.PartyBoardService;
import partyDuo.com.service.PartyService;

@Slf4j
@Controller
public class PartyBoardController {
	
	@Autowired
	PartyBoardService pbservice;
	
	@Autowired
	PartyService pservice;
	
	@Autowired
	HttpSession session;
	
	@GetMapping("/partyboard/insert")
	public String insert(Model model) {
		log.info("party_board_insert...");
		String user_character = (String)session.getAttribute("user_character");
		List<PartyVO> list = pservice.searchList("party_master",user_character);
		model.addAttribute("list", list);
		log.info("list: {}", list);
		return "partyboard/insert";			
	}
	
	@PostMapping("/partyboard/insertOK")
	public String insertOK(PartyBoardVO vo) {
		log.info("party_board_insertOK...");
		int result = pbservice.insertOK(vo);
		return "redirect:/partyboard/selectAll";			
	}
	
	@GetMapping("/partyboard/update")
	public String update(PartyBoardVO vo,Model model) {
		log.info("party_board_update...");
		PartyBoardVO vo2 = pbservice.selectOne(vo);
		log.info("{vo:{}",vo2);
		model.addAttribute("vo2", vo2);
		return "partyboard/update";			
	}
	
	@PostMapping("/partyboard/updateOK")
	public String updateOK(PartyBoardVO vo) {
		log.info("party_board_updateOK...");
		int result = pbservice.updateOK(vo);
		log.info("result:{}", result);
		return "redirect:/partyboard/selectOne?party_board_id="+vo.getParty_board_id();			
	}
	
	@GetMapping("/partyboard/delete")
	public String delete(PartyBoardVO vo,Model model) {
		log.info("party_board_delete...");
		PartyBoardVO vo2= pbservice.selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "partyboard/delete";			
	}
	
	@PostMapping("/partyboard/deleteOK")
	public String deleteOK(PartyBoardVO vo) {
		log.info("party_board_deleteOK...");
		int result = pbservice.deleteOK(vo);
		log.info("result:{}", result);
		return "redirect:/partyboard/selectAll";			
	}
	
	@GetMapping("/partyboard/selectOne")
	public String selectOne(PartyBoardVO vo, Model model) {
		log.info("party_board_selectOne...");
		PartyBoardVO vo2=pbservice.selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);
		return "partyboard/selectOne";			
	}
	
	@GetMapping("/partyboard/selectAll")
	public String selectAll(Model model) {
		log.info("party_board_selectAll...");
		List<PartyBoardVO> list = pbservice.selectAll();
		log.info("list: {}", list);
		model.addAttribute("list", list);
		return "partyboard/selectAll";			
	}
	
	@GetMapping("/partyboard/searchList")
	public String searchList(Model model, String searchKey,
			String searchWord, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5")int pageBlock) {
		log.info("party_board_searchList...");
		log.info(searchKey);
		log.info(searchWord);
		List<PartyBoardVO> list = pbservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
		model.addAttribute("list",list);
		log.info("list: {}", list);
		return "partyboard/selectAll";			
	}

}
