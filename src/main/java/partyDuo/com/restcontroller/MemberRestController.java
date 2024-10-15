package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.spiralmoon.maplestory.api.MapleStoryApi;
import dev.spiralmoon.maplestory.api.MapleStoryApiException;
import dev.spiralmoon.maplestory.api.dto.character.CharacterDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterListDTO;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.controller.CharacterController;
import partyDuo.com.service.CharacterService;

@Slf4j
@RestController
public class MemberRestController {

	@Autowired
	CharacterService service;
	
	@GetMapping("/member/idCheck")
	public String member_idCheck() {
		
		Map map=new HashMap<>();
		map.put("apiCheck","OK");
		log.info("/idCheck");
		return null;
	}
	@GetMapping("/member/apiCheck")
	public String member_apiCheck(String apikey) {
		String character=service.bonCharacter(service.foundOcid(apikey));	
		log.info("/apiCheck");
		return character;
	}
}
