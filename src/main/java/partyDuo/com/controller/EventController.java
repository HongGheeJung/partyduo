package partyDuo.com.controller;


import java.io.IOException;
import java.util.List;



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
	public String update(EventVO vo, Model model) {
		log.info("/event/update");		
		log.info("vo:{}", vo);

		EventVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "event/update";
	}

	@GetMapping("/event/delete")
	public String delete() {
		log.info("/event/delete");
		return "event/delete";
	}

	@GetMapping("/event/selectAll")
	public String selectAll(Model model) {
		log.info("/event/selectAll");
		
		List<EventVO> list = service.selectAll();
		model.addAttribute("list", list);

		return "event/selectAll";
	}

	@GetMapping("/event/searchList")
	public String searchList(Model model, @RequestParam(defaultValue = "month") String searchKey,
			@RequestParam(defaultValue = "01") String searchWord) {
		log.info("/event/searchList");
		log.info("searchKey:{}", searchKey);
		log.info("searchWord:{}", searchWord);


		List<EventVO> list = service.searchList(searchKey, searchWord);
		log.info("list.size():{}", list.size());

		model.addAttribute("list", list);
		
		return "event/selectAll";
	}

	@GetMapping("/event/selectOne")
	public String selectOne(EventVO vo, Model model) {
		log.info("/event/selectOne");	
		log.info("vo:{}", vo);

		EventVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "event/selectOne";
	}

	@PostMapping("/event/insertOK")
	public String insertOK(EventVO vo) throws IllegalStateException, IOException {
		log.info("/event/insertOK");
		log.info("vo:{}", vo);

		int result = service.insertOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/event/selectAll";
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
			return "redirect:/event/selectAll";
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
			return "redirect:/event/delete?event_id=" + vo.getEvent_id();
		}
	}
	
	

}
