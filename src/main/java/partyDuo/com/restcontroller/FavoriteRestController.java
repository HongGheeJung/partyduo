package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.FavoriteService;
import partyDuo.com.service.MemberService;

@Slf4j
@RestController
public class FavoriteRestController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	FavoriteService service;
	
	
	@GetMapping("/favorite/insert")
	public Map<String, String> favorite_insert(String character_name) {
		Map<String, String> map=new HashMap<>();
		String user_id=(String) session.getAttribute("user_id");
		log.info("user_id:{}",user_id);
		if(user_id==null) {
			map.put("result", "NotOK");
			return map;
		}
		log.info("chracter_name:{}",character_name);
		FavoriteVO vo=service.favorite(user_id, character_name);
		int result=service.favorite_insert(vo);
		log.info("result:{}",result);
		if (result==1) {
			map.put("result", "OK");
		}else {
			map.put("result", "NotOK");
		}
		return map;
	}
	
	@GetMapping("/favorite/delete")
	public Map<String, String> favorite_delete(String character_name){
		Map<String, String> map=new HashMap<>();
		String user_id=(String) session.getAttribute("user_id");
		if(user_id==null) {
			map.put("result", "NotOK");
			return map;
		}
		FavoriteVO vo=service.favorite(user_id, character_name);
		int result=service.favorite_delete(vo);
		log.info("result:{}", result);
		if (result!=0) {
			map.put("result", "OK");
		}else {
			map.put("result", "NotOK");
		}
		return map;
	}
	@GetMapping("/favorite/selectOne")
	public Map<String, String> favorite_selectOne(String character_name){
		Map<String, String> map=new HashMap<>();
		String user_id=(String) session.getAttribute("user_id");
		if(user_id==null) {
			map.put("result", "NotOK");
			return map;
		}
		FavoriteVO vo=service.favorite(user_id, character_name);
		FavoriteVO vo2=service.favorite_selectOne(vo);
		if(vo2!=null) {
			map.put("result", "OK");
		}else {
			map.put("result", "NotOK");
		}
		return map;
	}
	
	
}
