package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.spiralmoon.maplestory.api.MapleStoryApi;
import dev.spiralmoon.maplestory.api.MapleStoryApiException;
import dev.spiralmoon.maplestory.api.dto.character.CharacterDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterListDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.controller.CharacterController;
import partyDuo.com.model.EventVO;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.CharacterService;
import partyDuo.com.service.EventService;
import partyDuo.com.service.MemberService;

@Slf4j
@RestController
public class CalenderRestController {

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
