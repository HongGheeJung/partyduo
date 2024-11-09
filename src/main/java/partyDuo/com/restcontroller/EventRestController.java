package partyDuo.com.restcontroller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.EventVO;
import partyDuo.com.service.EventService;


@Slf4j
@RestController
public class EventRestController {

	@Autowired
	EventService service;

	
	@RequestMapping("/EventList")
	public List<EventVO> EventList(Model model,
			@RequestParam(defaultValue = "01") int party_id) throws Exception{
		log.info("/EventList");
		log.info("party_id:{}", party_id);
		List<EventVO> vo = service.searchListParty(party_id);
		log.info("vo:{}", vo);
		return vo;
	}
	
}
