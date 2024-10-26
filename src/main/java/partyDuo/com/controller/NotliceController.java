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
import partyDuo.com.model.NoticeVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.NoticeService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;
import partyDuo.com.service.ReportTrollService;

@Slf4j
@Controller
public class NotliceController {
	@Autowired
	NoticeService nservice;
	
	@GetMapping("/notice/insert")
	public String insert() {
		log.info("notice_insert...");
		
		return "notice/insert";
	}
	
	@PostMapping("/notice/insertOK")
	public String insertOK(NoticeVO vo) {
		log.info("notice_insertOK...");
		
		int result = nservice.insertOK(vo);
		log.info("result:{}", result);
		return "redirect:/notice/selectAll";
	}

	@GetMapping("/notice/update")
	public String update(NoticeVO vo, Model model) {
		log.info("notice_update...");
		NoticeVO vo2 = nservice.selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);
		return "notice/update";
	}

	@PostMapping("/notice/updateOK")
	public String updateOK(NoticeVO vo) {
		log.info("notice_updateOK...");
	
		int result = nservice.updateOK(vo);
		log.info("result:{}", result);
		return "redirect:/notice/selectAll";
				
	}
	
	@GetMapping("/notice/delete")
	public String delete(NoticeVO vo, Model model) {
		log.info("notice_delete...");
		NoticeVO vo2 = nservice.selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "notice/delete";
	}

	@PostMapping("/notice/deleteOK")
	public String deleteOK(NoticeVO vo) {
		log.info("notice_deleteOK...");
		int result = nservice.deleteOK(vo);
		log.info("result:{}", result);
		return "redirect:/notice/selectAll";
	}
	
	@GetMapping("/notice/selectOne")
	public String selectOne(NoticeVO vo, Model model) {
		log.info("notice_selectOne...");
		NoticeVO vo2= nservice.selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);		
		return "notice/selectOne";
	}
	
	@GetMapping("/notice/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "20") int pageBlock) {
		log.info("notice_selectAll...");
		List<NoticeVO> list= nservice.selectAllPageBlock(cpage, pageBlock);
		
		
		int total_rows = nservice.getTotalRows();// select count(*) total_rows from member;
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
		return "notice/selectAll";
	}
	
	@GetMapping("/notice/searchList")
	public String searchList(Model model, String searchKey,
			String searchWord, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "20") int pageBlock) {
		log.info("notice_searchList...");
		log.info(searchKey);
		log.info(searchWord);
		
		List<NoticeVO> list = nservice.searchListPageBlock(searchKey,searchWord,cpage,pageBlock);
		
		
		log.info("list: {}", list);
		int total_rows = nservice.getSearchTotalRows(searchKey, searchWord);// select count(*) total_rows from member;
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
		return "notice/selectAll";
	}
}
