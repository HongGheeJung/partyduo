package partyDuo.com.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.PartyVO;
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
	public String update(PartyVO vo,Model model) {
		log.info("party_update...");
		PartyVO vo2 = pservice.selectOne(vo);
		log.info("{vo:{}",vo2);
		model.addAttribute("vo2", vo2);
		return "party/update";			
	}
	@GetMapping("/party/delete")
	public String delete(PartyVO vo) {
		log.info("party_delete...");
		return "party/delete";			
	}
	@GetMapping("/party/selectOne")
	public String selectOne(PartyVO vo, Model model) {
		log.info("party_selectOne...");
		log.info("vo:{}", vo);

		PartyVO vo2 = pservice.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);
		return "party/selectOne";			
	}
	
	@GetMapping("/party/searchList")
	public String searchList(String searchKey,String searchWord) {
		log.info("party_searchList...");
		
		return "party/searchList";			
	}
	@PostMapping("/party/insertOK")
	public String insertOK(PartyVO vo) {
		log.info("party_insertOK...");
		log.info("vo:{}", vo);
		int result = pservice.insertOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/party/searchList";
		} else {
			return "redirect:/party/insert";
		}		
	}
	@PostMapping("/party/updateOK")
	public String updateOK(PartyVO vo) {
		log.info("party_updateOK...");
	
		int result = pservice.updateOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/party/selectOne?party_id=" + vo.getParty_id();
		} else {
			return "redirect:/party/update?party_id=" + vo.getParty_id();
		}			
	}
	
	@PostMapping("/party/deleteOK")
	public String deleteOK(PartyVO vo) {
		log.info("party_deleteOK...");
		
		int result = pservice.deleteOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/party/selectOne?party_id=" + vo.getParty_id();
		} else {
			return "redirect:/party/delete?party_id=" + vo.getParty_id();
		}			
	}
	
}
