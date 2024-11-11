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
import partyDuo.com.model.EventLikeVO;
@Slf4j
@Controller
public class EventLikeController {
	

	
	@Autowired
	EventLikeService service;


	@GetMapping("/admin/eventlike/delete")
	public String delete() {
		log.info("/event/delete");
		return "event/delete";
	}

	@GetMapping("/admin/eventlike/selectAll")
	public String selectAll(Model model) {
		log.info("/admin/eventlike/selectAll");
		
		List<EventLikeVO> list = service.selectAll();
		model.addAttribute("list", list);

		return "admin/eventlike/selectAll";
	}
	
	@PostMapping("/admin/eventlike/deleteOK")
	public String admindeleteOK(EventLikeVO vo) throws IllegalStateException, IOException {
		log.info("/eventlike/deleteOK");


		int result = service.deleteOK(vo);
		log.info("result:{}", result);

		
		return "redirect:/admin/eventlike/selectAll";

	}
	
	

}
