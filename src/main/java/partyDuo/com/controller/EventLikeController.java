package partyDuo.com.controller;


import java.io.IOException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.EventLikeService;
import partyDuo.com.service.EventService;

import partyDuo.com.model.EventLikeVO;

@Slf4j
@Controller
public class EventLikeController {
	
	@Autowired
	EventLikeService service;

	@GetMapping("/eventLike/insert")
	public String insert() {
		log.info("/eventLike/insert");
		return "eventLike/insert";
	}
	
	@GetMapping("/eventLike/delete")
	public String delete() {
		log.info("/eventLike/delete");
		return "eventLike/delete";
	}

	@GetMapping("/eventLike/selectAll")
	public String selectAll(Model model) {
		log.info("/eventLike/selectAll");
		
		List<EventLikeVO> list = service.selectAll();
		model.addAttribute("list", list);

		return "eventLike/selectAll";
	}


	@GetMapping("/eventLike/selectOne")
	public String selectOne(EventLikeVO vo, Model model) {
		log.info("/eventLike/selectOne");	
		log.info("vo:{}", vo);

		EventLikeVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "eventLike/selectOne";
	}

	@PostMapping("/eventLike/insertOK")
	public String insertOK(EventLikeVO vo) throws IllegalStateException, IOException {
		log.info("/eventLike/insertOK");
		log.info("vo:{}", vo);
		int likecheck =service.selectOneLikecheck(vo);
		log.info("likecheck:{}", likecheck);
		
		
		if(likecheck ==1) {
			int result = service.deleteOK(vo);
			log.info("result:{}", result);
			if (result == 1) {
				return "redirect:/eventLike/selectAll";
			} else {
				return "redirect:/event/selectAll";
			}
			
		}else {
			int result = service.insertOK(vo);
			log.info("result:{}", result);
			if (result == 1) {
				return "redirect:/eventLike/selectAll";
			} else {
				return "redirect:/event/selectAll";
			}
			
		}
		
	}

	@PostMapping("/eventLike/deleteOK")
	public String deleteOK(EventLikeVO vo) {
		log.info("/eventLike/deleteOK");
		log.info("vo:{}", vo);

		int result = service.deleteOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/eventLike/selectAll";
		} else {
			return "redirect:/eventLike/delete?event_id=" + vo.getEvent_id();
		}
	}
	
	

}
