package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.spiralmoon.maplestory.api.MapleStoryApi;
import dev.spiralmoon.maplestory.api.MapleStoryApiException;
import dev.spiralmoon.maplestory.api.dto.character.CharacterDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterListDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.controller.CharacterController;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.CharacterService;
import partyDuo.com.service.MemberService;

@Slf4j
@RestController
public class MemberRestController {

	@Autowired
	CharacterService cservice;
	@Autowired
	MemberService mservice;
	
	@GetMapping("/member/idCheck")
	public Map<String, String> member_idCheck(MemberVO vo) {
		log.info("/idCheck");
		Map map=new HashMap<>();
		MemberVO vo2=mservice.member_selectOne(vo);
		if(vo2==null) {
			map.put("result","OK");
		}else {
			map.put("result","NotOK");
		}	
		return map;
	}
	@GetMapping("/member/apiCheck")
	public Map<String, String> member_apiCheck(MemberVO vo) {
		
		log.info("/apiCheck");
		Map map=new HashMap<>();
		log.info("vo:{}", vo);
		MemberVO vo2=mservice.apiCheck(vo);
		if(vo2==null) {
			map.put("result","OK");
		}else {
			map.put("result","NotOK");
		}	
		return map;
	}
	@GetMapping("/member/apiToCharacter")
	public Map<String, String> member_apiToCharacter(String apikey) {
		log.info("/apiToCharacter");
		String ocid=cservice.foundOcid(apikey);
		log.info("ocid:{}", ocid);
		String character=cservice.bonCharacter(ocid);	
		Map map=new HashMap<>();
		map.put("character_name", character);
		map.put("ocid", ocid);
		return map;
	}
	
	@GetMapping("/checkLogin")
	@ResponseBody
	public Map<String, Object> checkLogin(HttpSession session) {
	    Map<String, Object> response = new HashMap<>();
	    Object user = session.getAttribute("user_character");
	    
	    if (user != null) {
	        // 로그인된 경우
	        response.put("loggedIn", true);
	        response.put("loginMessage", "사용자가 로그인되어 있습니다.");
	    } else {
	        // 로그인되지 않은 경우
	        response.put("loggedIn", false);
	        response.put("loginMessage", "로그인이 필요합니다. 로그인 후 이용해 주세요.");
	    }
	    return response;
	}
}
