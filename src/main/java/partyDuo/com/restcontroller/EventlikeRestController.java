package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
	public Map<String, String> eventLike_selectOne(Model model, String event_id){
		Map<String, String> map=new HashMap<>();
		String character_name= (String)session.getAttribute("user_character");
		if(character_name==null) {
			map.put("result", "NotOK");
			return map;
		}
		EventLikeVO vo = new EventLikeVO();
		vo.setCharacter_name(character_name);
		vo.setEvent_id(Integer.parseInt(event_id));

		
		EventLikeVO vo3 = new EventLikeVO();
		vo3.setEvent_id(Integer.parseInt(event_id));
		
		List<EventLikeVO> likelist = service.selectlist(vo);
		model.addAttribute("likelist", likelist);
		log.info("likelist:{}", likelist);
		
		int result = service.selectOneLikecheck(vo);
		if(result != 0) {
			log.info("result:{like}");
			map.put("result", "like");
			
		}else {
			log.info("result:{dislike}");
			map.put("result", "dislike");
		}
		
		
		return map;
	}
	
}
