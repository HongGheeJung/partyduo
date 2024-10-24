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
import partyDuo.com.model.PartyBoardCommentVO;
import partyDuo.com.model.PartyBoardNameDTO;
import partyDuo.com.model.PartyBoardVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.model.ReportTrollVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyBoardCommentService;
import partyDuo.com.service.PartyBoardService;
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
	
	
	
}
