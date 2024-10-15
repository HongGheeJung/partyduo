package partyDuo.com.controller;


import java.io.IOException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.ChatService;

import partyDuo.com.model.ChatVO;

@Slf4j
@Controller
public class ChatController {
	
	@Autowired
	ChatService service;

	@GetMapping("/chat/insert")
	public String insert() {
		log.info("/chat/insert");
		return "chat/insert";
	}
	
	@GetMapping("/chat/update")
	public String update(ChatVO vo, Model model) {
		log.info("/chat/update");		
		log.info("vo:{}", vo);

		ChatVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "chat/update";
	}

	@GetMapping("/chat/delete")
	public String delete() {
		log.info("/chat/delete");
		return "chat/delete";
	}

	@GetMapping("/chat/selectAll")
	public String selectAll(Model model) {
		log.info("/chat/selectAll");
		
		List<ChatVO> list = service.selectAll();
		model.addAttribute("list", list);

		return "chat/selectAll";
	}

	@GetMapping("/chat/searchList")
	public String searchList(Model model, @RequestParam(defaultValue = "month") String searchKey,
			@RequestParam(defaultValue = "01") String searchWord) {
		log.info("/chat/searchList");
		log.info("searchKey:{}", searchKey);
		log.info("searchWord:{}", searchWord);


		List<ChatVO> list = service.searchList(searchKey, searchWord);
		log.info("list.size():{}", list.size());

		model.addAttribute("list", list);
		
		return "chat/selectAll";
	}

	@GetMapping("/chat/selectOne")
	public String selectOne(ChatVO vo, Model model) {
		log.info("/chat/selectOne");	
		log.info("vo:{}", vo);

		ChatVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);

		return "chat/selectOne";
	}

	@PostMapping("/chat/insertOK")
	public String insertOK(ChatVO vo) throws IllegalStateException, IOException {
		log.info("/chat/insertOK");
		log.info("vo:{}", vo);

		int result = service.insertOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/chat/selectAll";
		} else {
			return "redirect:/chat/insert";
		}
	}

	@PostMapping("/chat/updateOK")
	public String updateOK(ChatVO vo) throws IllegalStateException, IOException {
		log.info("/chat/updateOK");
		log.info("vo:{}", vo);



		int result = service.updateOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/chat/selectAll";
		} else {
			return "redirect:/chat/update" ;
		}
	}

	@PostMapping("/chat/deleteOK")
	public String deleteOK(ChatVO vo) {
		log.info("/chat/deleteOK");
		log.info("vo:{}", vo);

		int result = service.deleteOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "redirect:/chat/selectAll";
		} else {
			return "redirect:/chat/delete?chat_id=" + vo.getChat_id();
		}
	}
	
	

}
