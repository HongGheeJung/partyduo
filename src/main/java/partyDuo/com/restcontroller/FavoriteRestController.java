package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.service.FavoriteService;

@Slf4j
@RestController
public class FavoriteRestController {
	
	@Autowired
	FavoriteService service;
	
	@GetMapping("/favorite/insert")
	public Map<String, String> favorite_insert(FavoriteVO vo) {
		Map<String, String> map=new HashMap<>();
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
	public Map<String, String> favorite_delete(FavoriteVO vo){
		Map<String, String> map=new HashMap<>();
		int result=service.favorite_delete(vo);
		log.info("result:{}", result);
		if (result==1) {
			map.put("result", "OK");
		}else {
			map.put("result", "NotOK");
		}
		return map;
	}
	@GetMapping("/favorite/selectOne")
	public Map<String, String> favorite_selectOne(FavoriteVO vo){
		Map<String, String> map=new HashMap<>();
//		FavoriteVO vo2=service.favorite_selectOne(vo);
//		if(vo2!=null) {
//			map.put("result", "OK");
//		}else {
//			map.put("result", "NotOK");
//		}
		return map;
	}
	
	
}
