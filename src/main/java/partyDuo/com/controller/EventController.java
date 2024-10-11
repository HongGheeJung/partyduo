package partyDuo.com.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.EventService;
import partyDuo.com.model.EventVO;

@Slf4j
@Controller
public class EventController {
	
	@Autowired
	EventService service;
	
	@GetMapping("/calendar")
	public String calendar() {
		log.info("/calendar");
		return "calendar";
	}
	
	@GetMapping("/event/insert")
	public String insert() {
		log.info("/event/insert");
		return "event/insert";
	}
	
	@GetMapping("/event/update")
	public String update() {
		log.info("/event/update");		
		return "event/update";
	}

	@GetMapping("/event/delete")
	public String delete() {
		log.info("/event/delete");
		return "event/delete";
	}

	@GetMapping("/event/selectAll")
	public String selectAll() {
		log.info("/event/selectAll");
	

		return "event/selectAll";
	}

	@GetMapping("/event/searchList")
	public String searchList(Model model, @RequestParam(defaultValue = "id") String searchKey,
			@RequestParam(defaultValue = "ad") String searchWord,
			@RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "5") int pageBlock) {
		log.info("/event/searchList");
		log.info("searchKey:{}", searchKey);
		log.info("searchWord:{}", searchWord);
		log.info("cpage:{}", cpage);
		log.info("pageBlock:{}", pageBlock);

//		List<EventVO> list = service.searchList(searchKey, searchWord);
		List<EventVO> list = service.searchListPageBlock(searchKey, searchWord,cpage,pageBlock);
		log.info("list.size():{}", list.size());

		model.addAttribute("list", list);

		// 디비로부터 얻은 검색결과의 모든 행수
//		int total_rows = service.getTotalRows();// select count(*) total_rows from event;
		// select count(*) total_rows from event where id like '%ad%';
		// select count(*) total_rows from event where name like '%ki%';
		int total_rows = service.getSearchTotalRows(searchKey, searchWord);
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

		return "event/selectAll";
	}

	@GetMapping("/event/selectOne")
	public String selectOne() {
		log.info("/event/selectOne");	

		return "event/selectOne";
	}

	@PostMapping("/event/insertOK")
	public String insertOK(EventVO vo) throws IllegalStateException, IOException {
		log.info("/event/insertOK");
		log.info("vo:{}", vo);

		int result = service.insertOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/event/insert";
		} else {
			return "redirect:/event/insert";
		}
	}

	@PostMapping("/event/updateOK")
	public String updateOK(EventVO vo) throws IllegalStateException, IOException {
		log.info("/event/updateOK");
		log.info("vo:{}", vo);



		int result = service.updateOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/event/selectOne";
		} else {
			return "redirect:/event/update" ;
		}
	}

	@PostMapping("/event/deleteOK")
	public String deleteOK(EventVO vo) {
		log.info("/event/deleteOK");
		log.info("vo:{}", vo);

		int result = service.deleteOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/event/selectAll";
		} else {
			return "redirect:/event/delete" ;
		}
	}
	
	

}
