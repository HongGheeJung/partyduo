package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberRestController {
	
	@GetMapping("/member/idCheck")
	public Map<String, String> member_idCheck() {
		Map map=new HashMap<>();
		map.put("idCheck","OK");
		log.info("/idCheck");
		return map;
	}
	@GetMapping("/member/apiCheck")
	public Map<String, String> member_apiCheck() {
		Map map=new HashMap<>();
		map.put("apiCheck","OK");
		log.info("/apiCheck");
		return map;
	}
}
