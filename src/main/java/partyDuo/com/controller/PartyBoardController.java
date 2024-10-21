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
import partyDuo.com.model.PartyBoardCommentVO;
import partyDuo.com.model.PartyBoardVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.PartyBoardCommentService;
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
	PartyBoardCommentService pbcservice;
	
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
	public String insertOK(PartyBoardVO vo,String boss_level,String boss_name) {
		log.info("party_board_insertOK...");
		vo.setBoss(boss_level+" "+boss_name);
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
		PartyVO vo3= new PartyVO();
		vo3.setParty_id(vo2.getParty_id());
		vo3=pservice.selectOne(vo3);
		model.addAttribute("vo3", vo3);
		log.info("vo3:{}", vo3);
		List<PartyBoardCommentVO> list = pbcservice.searchListPartyBoardId(Integer.toString(vo.getParty_board_id()));
		model.addAttribute("list",list);
		return "partyboard/selectOne";			
	}
	
	@GetMapping("/partyboard/selectAll")
	public String selectAll(Model model,@RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "20")int pageBlock) {
		log.info("party_board_selectAll...");
		List<PartyBoardVO> list = pbservice.selectAllPageBlock(cpage,pageBlock);
		log.info("list: {}", list);
		int total_rows = pbservice.getTotalRows();// select count(*) total_rows from member;
		log.info("total_rows:{}", total_rows);
		// int pageBlock = 5;//1개페이지에서 보여질 행수,파라메터로 받으면됨.
		int totalPageCount = 0;

		// 총행카운트와 페이지블럭을 나눌때의 알고리즘을 추가기
		if (total_rows / pageBlock == 0) {
			totalPageCount = 1;
		} else if (total_rows % pageBlock == 0) {
			totalPageCount = total_rows / pageBlock;
		} else {
			totalPageCount = total_rows / pageBlock + 1;
		}
		log.info("totalPageCount:{}", totalPageCount);

		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPage",cpage);
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
		int total_rows = pbservice.getSearchTotalRows(searchKey, searchWord);// select count(*) total_rows from member;
		log.info("total_rows:{}", total_rows);
		// int pageBlock = 5;//1개페이지에서 보여질 행수,파라메터로 받으면됨.
		int totalPageCount = 0;

		// 총행카운트와 페이지블럭을 나눌때의 알고리즘을 추가기
		if (total_rows / pageBlock == 0) {
			totalPageCount = 1;
		} else if (total_rows % pageBlock == 0) {
			totalPageCount = total_rows / pageBlock;
		} else {
			totalPageCount = total_rows / pageBlock + 1;
		}
		log.info("totalPageCount:{}", totalPageCount);

		model.addAttribute("totalPageCount", totalPageCount);
		
		return "partyboard/selectAll";			
	}

}
