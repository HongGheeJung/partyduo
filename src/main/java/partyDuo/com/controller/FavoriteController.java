package partyDuo.com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.service.FavoriteService;

@Slf4j
@Controller
public class FavoriteController {
	
	@Autowired
	FavoriteService service;
	
	@GetMapping("/favorite/insert")
	public String favorite_insert(FavoriteVO vo) {
		int result=service.favorite_insert(vo);
		log.info("result:{}",result);
		return "/character/info?character_name="+vo.getCharacter_name();
	}
	
	@GetMapping("/favorite/delete")
	public String favorite_delete(FavoriteVO vo){
		int result=service.favorite_delete(vo);
		log.info("result:{}", result);
		
		return "redirect:/character/info?character_name="+vo.getCharacter_name();
	}
}
