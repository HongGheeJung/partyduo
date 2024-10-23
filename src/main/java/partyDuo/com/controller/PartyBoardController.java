package partyDuo.com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.PartyBoardCommentVO;
import partyDuo.com.model.PartyBoardVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyBoardCommentService;
import partyDuo.com.service.PartyBoardService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;

@Slf4j
@Controller
public class PartyBoardController {

	@Autowired
	PartyBoardService pbservice;

	@Autowired
	PartyService pservice;
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	PartyListService plservice;
	
	@Autowired
	PartyBoardCommentService pbcservice;

	@Autowired
	HttpSession session;

	@GetMapping("/partyboard/insert")
	public String insert(Model model) {
		log.info("party_board_insert...");
		String user_character = (String) session.getAttribute("user_character");
		List<PartyVO> list = pservice.searchList("party_master", user_character);
		model.addAttribute("list", list);
		log.info("list: {}", list);
		return "partyboard/insert";
	}

	@PostMapping("/partyboard/insertOK")
	public String insertOK(PartyBoardVO vo, String boss_level, String boss_name) {
		log.info("party_board_insertOK...");
		vo.setBoss(boss_level + " " + boss_name);
		int result = pbservice.insertOK(vo);
		return "redirect:/partyboard/selectAll";
	}

	@GetMapping("/partyboard/update")
	public String update(PartyBoardVO vo, Model model) {
		log.info("party_board_update...");
		PartyBoardVO vo2 = pbservice.selectOne(vo);
		log.info("{vo:{}", vo2);
		model.addAttribute("vo2", vo2);
		return "partyboard/update";
	}

	@PostMapping("/partyboard/updateOK")
	public String updateOK(PartyBoardVO vo, String boss_level, String boss_name) {
		log.info("party_board_updateOK...");
		vo.setBoss(boss_level + " " + boss_name);
		int result = pbservice.updateOK(vo);
		log.info("result:{}", result);
		return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	}

	@GetMapping("/partyboard/delete")
	public String delete(PartyBoardVO vo, Model model) {
		log.info("party_board_delete...");
		PartyBoardVO vo2 = pbservice.selectOne(vo);
		model.addAttribute("vo2", vo2);
		return "partyboard/delete";
	}

	@PostMapping("/partyboard/deleteOK")
	public String deleteOK(PartyBoardVO vo) {
		log.info("party_board_deleteOK...");
		int result = pbservice.deleteOK(vo);
		log.info("result:{}", result);
		return "redirect:/partyboard/selectAll";
	}

	@GetMapping("/partyboard/selectOne")
	public String selectOne(PartyBoardVO vo, Model model) {
		log.info("party_board_selectOne...");
		PartyBoardVO vo2 = pbservice.selectOne(vo);
		log.info("vo2:{}", vo2);
		model.addAttribute("vo2", vo2);
		PartyVO vo3 = new PartyVO();
		vo3.setParty_id(vo2.getParty_id());
		vo3 = pservice.selectOne(vo3);
		model.addAttribute("vo3", vo3);
		log.info("vo3:{}", vo3);
		
		String bossName = vo2.getBoss().replaceAll("(이지|노멀|하드|카오스|익스트림)\\s*", "");
		String bossImagePath = "/boss_img/" + bossName + ".png";
		List<PartyBoardCommentVO> list = pbcservice.searchListPartyBoardId(Integer.toString(vo.getParty_board_id()));
		model.addAttribute("list", list);
		model.addAttribute("bossImagePath", bossImagePath);
		
		//파티원 목록 넘기기
		List<PartyListVO> list2 = plservice.searchList("party_id", Integer.toString(vo3.getParty_id()));
		List<MemberVO> listmember = new ArrayList<>();
		List<MemberVO> listqueue = new ArrayList<>();
		
		for (PartyListVO vo4 : list2) {
			if (vo4.getParty_join() == true) {
				MemberVO vo5 = new MemberVO();
				vo5.setMember_id(vo4.getMember_id());
				vo5 = mservice.member_selectOneByMember_id(vo5);
				log.info("vo5{}", vo5);
				listmember.add(vo5);
			} else if (vo4.getParty_join() == false) {
				MemberVO vo5 = new MemberVO();
				vo5.setMember_id(vo4.getMember_id());
				log.info("vo5{}", vo5);
				vo5 = mservice.member_selectOneByMember_id(vo5);
				log.info("vo5{}", vo5);
				listqueue.add(vo5);
			}
		}
		
		
		
		model.addAttribute("listmember", listmember);
		model.addAttribute("listqueue", listqueue);
		return "partyboard/selectOne";
	}

	@GetMapping("/partyboard/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "20") int pageBlock) {
		log.info("party_board_selectAll...");
		List<PartyBoardVO> list = pbservice.selectAllPageBlock(cpage, pageBlock);
		List<PartyVO> list2 = pservice.selectAll(cpage, pageBlock);

		List<PartyBoardNameDTO> list3 = list.stream()
			    .map((PartyBoardVO vo) -> {
			        // party_id가 일치하는 파티의 이름과 월드를 찾음
			        PartyVO party = list2.stream()
			            .filter(p -> p.getParty_id() == vo.getParty_id()) // int 비교는 == 사용
			            .findFirst()
			            .orElseThrow(() -> new IllegalStateException("파티 정보를 찾을 수 없습니다."));
			        	
			        String bossName = vo.getBoss().replaceAll("(이지|노멀|하드|카오스|익스트림)\\s*", "");
		            String bossImagePath = "/boss_img/" + bossName + ".png";
		            
					// DTO 객체 생성
					return new PartyBoardNameDTO(vo, party.getParty_name(), party.getParty_world(),bossImagePath);
				})
				.collect(Collectors.toList());

		log.info("list: {}", list);
		int total_rows = pbservice.getTotalRows();// select count(*) total_rows from member;
		log.info("total_rows:{}", total_rows);
		// int pageBlock = 5;//1개페이지에서 보여질 행수,파라메터로 받으면됨.
		int totalPageCount = 0;

		// 총행카운트와 페이지블럭을 나눌때의 알고리즘을 추가기
		if (total_rows / pageBlock == 0) {
			totalPageCount = 1;
		} else if (total_rows % pageBlock == 0) {
			totalPageCount = total_rows / pageBlock;
		} else {
			totalPageCount = total_rows / pageBlock + 1;
		}
		log.info("totalPageCount:{}", totalPageCount);

		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPage", cpage);
		model.addAttribute("list3", list3);
		return "partyboard/selectAll";
	}

	@GetMapping("/partyboard/searchList")
	public String searchList(Model model, String searchKey,
			String searchWord, @RequestParam(defaultValue = "1") int cpage,
			@RequestParam(defaultValue = "20") int pageBlock) {
		log.info("party_board_searchList...");
		log.info(searchKey);
		log.info(searchWord);
		List<PartyBoardVO> list = new ArrayList<>();
		List<PartyVO> list2 = new ArrayList<>();
		
		// searchKey에 따라 로직 분기
	    if (searchKey.equals("world")) {
	        list = pbservice.selectAll(cpage, pageBlock); // 모든 파티 게시판 조회
	        list2 = pservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock); // 월드 검색된 파티 목록
	        log.info("PartyBoard List: {}", list);
	        log.info("Party List: {}", list2);

	        final List<PartyBoardVO> partyBoardList = list;
	        List<PartyBoardNameDTO> list3 = list2.stream()
	        	    .flatMap(party -> {
	        	        Optional<PartyBoardVO> optionalBoard = partyBoardList.stream()
	        	            .filter(board -> board.getParty_id() == party.getParty_id())
	        	            .findFirst();

	        	        return optionalBoard.map(board -> {
	        	            String bossName = board.getBoss().replaceAll("(이지|노멀|하드|카오스|익스트림)\\s*", "");
	        	            String bossImagePath = "/boss_img/" + bossName + ".png";

	        	            // 일치하는 경우 DTO 생성
	        	            return new PartyBoardNameDTO(board, party.getParty_name(), party.getParty_world(), bossImagePath);
	        	        }).stream(); // Optional을 Stream으로 변환
	        	    })
	        	    .collect(Collectors.toList());

	        log.info("list3: {}", list3);
	        model.addAttribute("list3", list3);

	    } else {
	        list = pbservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
	        list2 = pservice.selectAll(cpage, pageBlock); // 모든 파티 조회
	        final List<PartyVO> partyList = list2;

	        List<PartyBoardNameDTO> list3 = list.stream()
	            .map(board -> {
	                Optional<PartyVO> optionalParty = partyList.stream()
	                    .filter(party -> party.getParty_id() == board.getParty_id())
	                    .findFirst();

	                // 보스 이름에서 난이도 제거 후 이미지 경로 설정
	                String bossName = board.getBoss().replaceAll("(이지|노멀|하드|카오스|익스트림)\\s*", "");
	                String bossImagePath = "/boss_img/" + bossName + ".png";

	                return optionalParty
	                    .map(party -> new PartyBoardNameDTO(board, party.getParty_name(), party.getParty_world(), bossImagePath))
	                    .orElseThrow(() -> new IllegalStateException("파티 정보를 찾을 수 없습니다: " + board.getParty_id()));
	            })
	            .collect(Collectors.toList());

	        model.addAttribute("list3", list3);
	    }
		
		log.info("list: {}", list);
		int total_rows = pbservice.getSearchTotalRows(searchKey, searchWord);// select count(*) total_rows from member;
		log.info("total_rows:{}", total_rows);
		// int pageBlock = 5;//1개페이지에서 보여질 행수,파라메터로 받으면됨.
		int totalPageCount = 0;

		// 총행카운트와 페이지블럭을 나눌때의 알고리즘을 추가기
		if (total_rows / pageBlock == 0) {
			totalPageCount = 1;
		} else if (total_rows % pageBlock == 0) {
			totalPageCount = total_rows / pageBlock;
		} else {
			totalPageCount = total_rows / pageBlock + 1;
		}
		log.info("totalPageCount:{}", totalPageCount);

		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPage", cpage);
		return "partyboard/selectAll";
	}
	
}
