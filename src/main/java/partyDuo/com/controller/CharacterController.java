package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.CharacterService;

@Slf4j
@Controller
public class CharacterController {
	@Autowired
	CharacterService service;
	
	@GetMapping("/character/info")
	public String member_insert() {
		String result=service.character_info();
		log.info("/charcter/info, result:", result);
		return "character/info";
	}
}
