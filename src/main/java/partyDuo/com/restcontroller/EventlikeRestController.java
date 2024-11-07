package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.EventLikeVO;
import partyDuo.com.service.EventLikeService;
import partyDuo.com.service.MemberService;

@Slf4j
@RestController
public class EventlikeRestController {
	
	@Autowired
	HttpSession session;
	
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	EventLikeService service;
	
	
	@GetMapping("/eventLike/insert")
	public Map<String, String> eventLike_insert(String event_id) {
		Map<String, String> map=new HashMap<>();
		
		String character_name= (String)session.getAttribute("user_character");
		log.info("character_name:{}",character_name);
		if(character_name==null) {
			map.put("result", "NotOK");
			return map;
		}
		EventLikeVO vo = new EventLikeVO();
		vo.setCharacter_name(character_name);
		vo.setEvent_id(Integer.parseInt(event_id));
		int result=service.insertOK(vo);
		
		log.info("result:{}",result);
		if (result==1) {
			map.put("result", "OK");
		}else {
			map.put("result", "NotOK");
		}
		return map;
	}
	
	@GetMapping("/eventLike/delete")
	public Map<String, String> eventLike_delete(String event_id){
		Map<String, String> map=new HashMap<>();
		String character_name= (String)session.getAttribute("user_character");
		log.info("character_name:{}",character_name);
		if(character_name==null) {
			map.put("result", "NotOK");
			return map;
		}
		EventLikeVO vo = new EventLikeVO();
		vo.setCharacter_name(character_name);
		vo.setEvent_id(Integer.parseInt(event_id));
		int result=service.deleteOK(vo);
		log.info("result:{}", result);
		if (result!=0) {
			map.put("result", "OK");
		}else {
			map.put("result", "NotOK");
		}
		return map;
	}
	@GetMapping("/eventLike/selectOne")
	public Map<String, String> eventLike_selectOne(String event_id){
		Map<String, String> map=new HashMap<>();
		String character_name= (String)session.getAttribute("user_character");
		if(character_name==null) {
			map.put("result", "NotOK");
			return map;
		}
		EventLikeVO vo = new EventLikeVO();
		vo.setCharacter_name(character_name);
		vo.setEvent_id(Integer.parseInt(event_id));
		int like =service.selectOneLikecheck(vo);
		
		if(like == 1) {
			map.put("result", "like");
		}else if(like == 0)  {
			map.put("result", "dislike");
		}else {
			map.put("result", "NotOK");
		}
		return map;
	}
	
}
