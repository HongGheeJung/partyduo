package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import partyDuo.com.model.PartyVO;
import partyDuo.com.service.PartyService;

@Slf4j
@RestController
public class PartyRestController {

	@Autowired
	PartyService pservice;
	
	@PostMapping("/party/nameCheck")
	public Map<String, String> partyNamecheck(PartyVO vo) {
		log.info("/idCheck");
		Map map=new HashMap<>();
		PartyVO vo2=pservice.selectOnePname(vo);
		log.info("vo2:{}",vo2);
		if(vo2==null) {
			map.put("result","OK");
		}else {
			map.put("result","NotOK");
		}	
		return map;
	}
}
