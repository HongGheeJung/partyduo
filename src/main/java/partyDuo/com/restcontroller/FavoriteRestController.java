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
	
	@Autowired
	MemberService mservice;
	
	
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
	
	@GetMapping("/favorite/searchList")
	public Map<String, Object> favorite_searchList(String id, Model model, int cpage) {
		int pageBlock=5;
		log.info("searchList");
		Map<String, Object> data=new HashMap<>();
		MemberVO mvo=new MemberVO();
		mvo.setId(id);
		int member_id=mservice.member_selectOne(mvo).getMember_id();
		List<FavoriteVO> list=service.favorite_searchList("member_id", member_id, cpage, pageBlock);
		data.put("list", list);
		int total_rows=service.getSearchTotalRows("member_id", mvo.getMember_id());
		
		Integer totalPageCount = 0;
		if (total_rows / pageBlock == 0) {
			totalPageCount = 1;
		} else if (total_rows % pageBlock == 0) {
			totalPageCount = total_rows / pageBlock;
		} else {
			totalPageCount = total_rows / pageBlock + 1;
		}
		data.put("totlPageCount", totalPageCount);
		
		return data;
	}
}
