package partyDuo.com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;

import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.model.ReportTrollVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;
import partyDuo.com.service.ReportTrollService;

@Slf4j
@Controller
public class ReportTrollController {
	@Autowired
	ReportTrollService rtservice;
	
	@GetMapping("/reporttroll/insert")
	public String insert() {
		log.info("report_troll_insert...");
		
		return "reporttroll/insert";
	}
	
	@PostMapping("/reporttroll/insertOK")
	public String insertOK(ReportTrollVO vo) {
		log.info("report_troll_insertOK...");
		
		int result = rtservice.insertOK(vo);
		log.info("result:{}", result);
		return "redirect:/reporttroll/selectAll";
	}

	@GetMapping("/reporttroll/update")
	public String update(ReportTrollVO vo, Model model) {
		log.info("report_troll_update...");
		ReportTrollVO vo2 = rtservice.selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);
		return "reporttroll/update";
	}

	@PostMapping("/reporttroll/updateOK")
	public String updateOK(ReportTrollVO vo) {
		log.info("report_troll_updateOK...");
	
		int result = rtservice.updateOK(vo);
		log.info("result:{}", result);
		return "redirect:/reporttroll/selectAll";
				
	}
	
	@GetMapping("/reporttroll/delete")
	public String delete(ReportTrollVO vo, Model model) {
		log.info("report_troll_delete...");
		ReportTrollVO vo2 = rtservice.selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "reporttroll/delete";
	}

	@PostMapping("/reporttroll/deleteOK")
	public String deleteOK(ReportTrollVO vo) {
		log.info("report_troll_deleteOK...");
		int result = rtservice.deleteOK(vo);
		log.info("result:{}", result);
		return "redirect:/reporttroll/selectAll";
	}
	
	@GetMapping("/reporttroll/selectOne")
	public String selectOne(ReportTrollVO vo, Model model) {
		log.info("report_troll_selectOne...");
		ReportTrollVO vo2= rtservice.selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);
		
		return "reporttroll/selectOne";
	}
	
	@GetMapping("/reporttroll/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "20") int pageBlock) {
		log.info("report_troll_selectAll...");
		List<ReportTrollVO> list= rtservice.selectAllPageBlock(cpage, pageBlock);
		
		
		int total_rows = rtservice.getTotalRows();// select count(*) total_rows from member;
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
		model.addAttribute("currentPage", cpage);
		model.addAttribute("list",list);
		return "reporttroll/selectAll";
	}
	
	@GetMapping("/reporttroll/searchList")
	public String searchList(Model model, String searchKey,
			String searchWord, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "20") int pageBlock) {
		log.info("report_troll_searchList...");
		log.info(searchKey);
		log.info(searchWord);
		
		List<ReportTrollVO> list = rtservice.searchListPageBlock(searchKey,searchWord,cpage,pageBlock);
		
		
		log.info("list: {}", list);
		int total_rows = rtservice.getSearchTotalRows(searchKey, searchWord);// select count(*) total_rows from member;
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
		model.addAttribute("currentPage", cpage);
		model.addAttribute("list", list);
		return "reporttroll/selectAll";
	}
}
