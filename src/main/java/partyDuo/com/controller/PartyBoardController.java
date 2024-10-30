package partyDuo.com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import partyDuo.com.model.PartyBoardNameDTO;
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

	    try {
	        // 세션에서 user_character 가져오기
	        String user_character = (String) session.getAttribute("user_character");
	        
	        // user_character가 null이거나 비어있을 경우 오류 처리
	        if (user_character == null || user_character.trim().isEmpty()) {
	            model.addAttribute("errorMessage", "로그인 정보가 유효하지 않습니다. 다시 로그인해 주세요.");
	            return "redirect:/member/login";  // 로그인 페이지로 리다이렉트
	        }

	        // 파티 정보를 검색
	        List<PartyVO> list = pservice.searchList("party_master", user_character);
	        model.addAttribute("list", list);

	        log.info("list: {}", list);
	        
	    } catch (Exception e) {
	        // 예상치 못한 오류 발생 시 처리
	        log.error("파티 게시판 로딩 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판을 로드하는 중 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
	        return "partyboard/selectAll";  // 오류 페이지로 이동
	    }

	    return "partyboard/insert";
	}

	@PostMapping("/partyboard/insertOK")
	public String insertOK(PartyBoardVO vo, String boss_level, String boss_name, Model model) {
	    log.info("party_board_insertOK...");

	    // VO 필드가 유효한지 체크
	    if (vo == null) {
	        model.addAttribute("errorMessage", "파티 게시판 정보를 입력해 주세요.");
	        return "partyboard/insert"; // 다시 입력 페이지로 이동
	    }
	    
	    // 필수 필드 체크
	    if (vo.getParty_id() == 0) {
	        model.addAttribute("errorMessage", "파티 번호가 유효하지 않습니다. 다시 시도해 주세요.");
	        return "partyboard/insert";
	    }
	    
	    if (vo.getReq_pwr() <= 0) {
	        model.addAttribute("errorMessage", "요구 전투력은 0보다 커야 합니다. 다시 입력해 주세요.");
	        return "partyboard/insert";
	    }

	    if (boss_level == null || boss_level.trim().isEmpty() || boss_name == null || boss_name.trim().isEmpty()) {
	        model.addAttribute("errorMessage", "보스 레벨과 보스 이름을 입력해 주세요.");
	        return "partyboard/insert"; // 다시 입력 페이지로 이동
	    }

	    if (vo.getParty_board_memo() == null || vo.getParty_board_memo().trim().isEmpty()) {
	        model.addAttribute("errorMessage", "메모를 입력해 주세요.");
	        return "partyboard/insert";
	    }

	    if (vo.getParty_board_content() == null || vo.getParty_board_content().trim().isEmpty()) {
	        model.addAttribute("errorMessage", "게시판 내용을 입력해 주세요.");
	        return "partyboard/insert";
	    }

	    if (vo.getParty_board_writer() == null || vo.getParty_board_writer().trim().isEmpty()) {
	        model.addAttribute("errorMessage", "작성자를 입력해 주세요.");
	        return "partyboard/insert";
	    }

	    // 보스 정보 설정
	    vo.setBoss(boss_level + " " + boss_name);
	    
	    try {
	        // 데이터베이스에 파티 게시판 데이터 추가
	        int result = pbservice.insertOK(vo);
	        
	        // 데이터베이스 저장 실패 시 처리
	        if (result == 0) {
	            model.addAttribute("errorMessage", "파티 게시판 등록에 실패했습니다. 다시 시도해 주세요.");
	            return "partyboard/insert";
	        }

	    } catch (Exception e) {
	        // 데이터베이스 오류 발생 시 처리
	        log.error("파티 게시판 등록 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판 등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/insert";
	    }

	    return "redirect:/partyboard/selectAll";
	}

	@GetMapping("/partyboard/update")
	public String update(PartyBoardVO vo, Model model) {
	    log.info("party_board_update...");

	    // VO 유효성 체크
	    if (vo == null || vo.getParty_board_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 게시판 정보를 제공해 주세요.");
	        return "partyboard/selectAll"; // 유효하지 않은 경우 목록으로 이동
	    }

	    try {
	        // 데이터베이스에서 해당 파티 게시판 항목 조회
	        PartyBoardVO vo2 = pbservice.selectOne(vo);
	        
	        // 데이터가 없는 경우 처리
	        if (vo2 == null) {
	            model.addAttribute("errorMessage", "해당 파티 게시판 정보를 찾을 수 없습니다.");
	            return "partyboard/selectAll"; // 데이터가 없는 경우 목록으로 이동
	        }

	        log.info("vo: {}", vo2);
	        model.addAttribute("vo2", vo2);
	    } catch (Exception e) {
	        // 데이터베이스 오류 발생 시 처리
	        log.error("파티 게시판 조회 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll";
	    }

	    return "partyboard/update";
	}

	@PostMapping("/partyboard/updateOK")
	public String updateOK(PartyBoardVO vo, String boss_level, String boss_name, Model model) {
	    log.info("party_board_updateOK...");

	    // 필수 항목 검증
	    if (vo == null) {
	        model.addAttribute("errorMessage", "유효한 파티 게시판 정보를 입력해 주세요.");
	        return "partyboard/update";
	    }

	    if (vo.getParty_board_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 게시판 번호가 필요합니다.");
	        return "partyboard/update";
	    }

	    if (boss_level == null || boss_level.trim().isEmpty() || boss_name == null || boss_name.trim().isEmpty()) {
	        model.addAttribute("errorMessage", "보스 레벨과 보스 이름은 필수 항목입니다.");
	        return "partyboard/update";
	    }

	    if (vo.getParty_board_writer() == null || vo.getParty_board_writer().trim().isEmpty()) {
	        model.addAttribute("errorMessage", "작성자는 필수 입력 항목입니다.");
	        return "partyboard/update";
	    }

	    // 보스 정보 설정
	    vo.setBoss(boss_level + " " + boss_name);

	    try {
	        int result = pbservice.updateOK(vo);
	        if (result == 0) {
	            model.addAttribute("errorMessage", "파티 게시판 업데이트에 실패했습니다. 다시 시도해 주세요.");
	            return "partyboard/update";
	        }
	        log.info("result: {}", result);
	    } catch (Exception e) {
	        log.error("파티 게시판 업데이트 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판 업데이트 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/update";
	    }

	    return "redirect:/partyboard/selectOne?party_board_id=" + vo.getParty_board_id();
	}

	@GetMapping("/partyboard/delete")
	public String delete(PartyBoardVO vo, Model model) {
	    log.info("party_board_delete...");

	    // 필수 항목 검증
	    if (vo == null || vo.getParty_board_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 게시판 번호가 필요합니다.");
	        return "partyboard/selectAll"; // 적절한 목록 페이지로 이동
	    }

	    try {
	        PartyBoardVO vo2 = pbservice.selectOne(vo);
	        if (vo2 == null) {
	            model.addAttribute("errorMessage", "삭제하려는 파티 게시판 정보를 찾을 수 없습니다.");
	            return "partyboard/selectAll"; // 정보가 없을 경우 목록 페이지로 이동
	        }

	        model.addAttribute("vo2", vo2);
	    } catch (Exception e) {
	        log.error("파티 게시판 정보 조회 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판 정보를 불러오는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll"; // 오류 발생 시 목록 페이지로 이동
	    }

	    return "partyboard/delete";
	}

	@PostMapping("/partyboard/deleteOK")
	public String deleteOK(PartyBoardVO vo, Model model) {
	    log.info("party_board_deleteOK...");

	    // 필수 항목 검증
	    if (vo == null || vo.getParty_board_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 게시판 ID가 필요합니다.");
	        return "partyboard/selectAll"; // 유효하지 않은 경우 목록 페이지로 이동
	    }

	    try {
	        int result = pbservice.deleteOK(vo);
	        log.info("result: {}", result);

	        if (result == 0) {
	            model.addAttribute("errorMessage", "파티 게시판 삭제에 실패했습니다. 다시 시도해 주세요.");
	            return "partyboard/selectAll"; // 삭제 실패 시 목록 페이지로 이동
	        }
	    } catch (Exception e) {
	        log.error("파티 게시판 삭제 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll"; // 오류 발생 시 목록 페이지로 이동
	    }

	    return "redirect:/partyboard/selectAll";
	}


	@GetMapping("/partyboard/selectOne")
	public String selectOne(PartyBoardVO vo, Model model) {
	    log.info("party_board_selectOne...");
	    
	    // VO 필드 검증
	    if (vo == null || vo.getParty_board_id() == 0) {
	        model.addAttribute("errorMessage", "유효한 파티 게시판 정보를 입력해 주세요.");
	        return "partyboard/selectAll";
	    }
	    
	    try {
	        PartyBoardVO vo2 = pbservice.selectOne(vo);
	        if (vo2 == null) {
	            model.addAttribute("errorMessage", "파티 게시판 정보를 찾을 수 없습니다.");
	            return "partyboard/selectAll";
	        }
	        
	        log.info("vo2:{}", vo2);
	        model.addAttribute("vo2", vo2);

	        // 파티 정보 조회
	        PartyVO vo3 = new PartyVO();
	        vo3.setParty_id(vo2.getParty_id());
	        vo3 = pservice.selectOne(vo3);
	        if (vo3 == null) {
	            model.addAttribute("errorMessage", "파티 정보를 찾을 수 없습니다.");
	            return "partyboard/selectAll";
	        }
	        
	        model.addAttribute("vo3", vo3);
	        log.info("vo3:{}", vo3);

	        // 보스 이미지 경로 생성
	        String bossName = vo2.getBoss().replaceAll("(이지|노멀|하드|카오스|익스트림)\\s*", "");
	        String bossImagePath = "/boss_img/" + bossName + ".png";
	        model.addAttribute("bossImagePath", bossImagePath);

	        // 댓글 목록 조회
	        List<PartyBoardCommentVO> list = pbcservice.searchListPartyBoardId(Integer.toString(vo.getParty_board_id()));
	        model.addAttribute("list", list);

	        // 파티원 목록 조회
	        List<PartyListVO> list2 = plservice.searchList("party_id", Integer.toString(vo3.getParty_id()));
	        List<MemberVO> listmember = new ArrayList<>();
	        List<MemberVO> listqueue = new ArrayList<>();

	        for (PartyListVO vo4 : list2) {
	            MemberVO vo5 = new MemberVO();
	            vo5.setMember_id(vo4.getMember_id());
	            vo5 = mservice.member_selectOneByMember_id(vo5);
	            
	            if (vo5 == null) {
	                continue; // 회원 정보를 찾을 수 없는 경우 건너뜀
	            }

	            log.info("vo5: {}", vo5);
	            if (vo4.getParty_join()) {
	                listmember.add(vo5);
	            } else {
	                listqueue.add(vo5);
	            }
	        }

	        model.addAttribute("listmember", listmember);
	        model.addAttribute("listqueue", listqueue);

	    } catch (Exception e) {
	        log.error("파티 게시판 조회 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll";
	    }
	    
	    return "partyboard/selectOne";
	}


	@GetMapping("/partyboard/selectAll")
	public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
	                        @RequestParam(defaultValue = "20") int pageBlock) {
	    log.info("party_board_selectAll...");

	    try {
	        int total_rows = pbservice.getTotalRows();
	        int totalPageCount = (total_rows + pageBlock - 1) / pageBlock; // 총 페이지 수 계산

	        // cpage가 1보다 작거나 totalPageCount보다 크면 범위 내로 조정
	        if (cpage < 1) {
	            cpage = 1;
	        } else if (cpage > totalPageCount) {
	            cpage = totalPageCount;
	        }

	        List<PartyBoardVO> list = pbservice.selectAllPageBlock(cpage, pageBlock);
	        List<PartyVO> list2 = pservice.selectAll(cpage, pageBlock);

	        if (list == null || list.isEmpty()) {
	            model.addAttribute("errorMessage", "파티 게시판 정보가 없습니다.");
	            return "partyboard/selectAll";
	        }

	        if (list2 == null || list2.isEmpty()) {
	            model.addAttribute("errorMessage", "파티 정보가 없습니다.");
	            return "partyboard/selectAll";
	        }

	        List<PartyBoardNameDTO> list3 = list.stream()
	                .map(vo -> {
	                    try {
	                        PartyVO party = list2.stream()
	                                .filter(p -> p.getParty_id() == vo.getParty_id())
	                                .findFirst()
	                                .orElseThrow(() -> new IllegalStateException("파티 정보를 찾을 수 없습니다."));

	                        String bossName = vo.getBoss().replaceAll("(이지|노멀|하드|카오스|익스트림)\\s*", "");
	                        String bossImagePath = "/boss_img/" + bossName + ".png";

	                        return new PartyBoardNameDTO(vo, party.getParty_name(), party.getParty_world(), bossImagePath);
	                    } catch (Exception e) {
	                        log.error("파티 정보를 매칭하는 중 오류 발생: {}", e.getMessage());
	                        return null;
	                    }
	                })
	                .filter(Objects::nonNull)
	                .collect(Collectors.toList());

	        model.addAttribute("list3", list3);
	        model.addAttribute("totalPageCount", totalPageCount);
	        model.addAttribute("currentPage", cpage);

	    } catch (Exception e) {
	        log.error("파티 게시판 조회 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "파티 게시판 조회 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll";
	    }

	    return "partyboard/selectAll";
	}
	
	@GetMapping("/partyboard/searchList")
	public String searchList(Model model, String searchKey, String searchWord, @RequestParam(defaultValue = "1") int cpage,
	                         @RequestParam(defaultValue = "20") int pageBlock) {
	    log.info("party_board_searchList...");
	    log.info("Search Key: {}", searchKey);
	    log.info("Search Word: {}", searchWord);

	    List<PartyBoardVO> list = new ArrayList<>();
	    List<PartyVO> list2 = new ArrayList<>();
	    List<PartyBoardNameDTO> list3;
	    try {
	        // 총 행 수를 구하여 페이지 수 계산
	        int total_rows = pbservice.getSearchTotalRows(searchKey, searchWord);
	        log.info("total_rows:{}", total_rows);
	        int totalPageCount;

	        if (total_rows == 0) {
	            totalPageCount = 1;
	        } else if (total_rows % pageBlock == 0) {
	            totalPageCount = total_rows / pageBlock;
	        } else {
	            totalPageCount = total_rows / pageBlock + 1;
	        }
	        		
	        log.info("totalPageCount:{}", totalPageCount);
	        
	        if (cpage < 1) {
	            cpage = 1;
	        } else if (cpage > totalPageCount) {
	            cpage = totalPageCount;
	        }
	        
	        model.addAttribute("totalPageCount", totalPageCount);
	        model.addAttribute("currentPage", cpage);
	    } catch (Exception e) {
	        log.error("페이지 계산 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "페이지 정보를 계산하는 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll";
	    }
	    
	    try {
	        // searchKey에 따라 로직 분기
	        if ("world".equals(searchKey)) {
	            list = pbservice.selectAllPageBlock(cpage, pageBlock); // 모든 파티 게시판 조회
	            list2 = pservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock); // 월드 검색된 파티 목록
	            log.info("PartyBoard List: {}", list);
	            log.info("Party List: {}", list2);

	            if (list == null || list.isEmpty() || list2 == null || list2.isEmpty()) {
	                model.addAttribute("errorMessage", "해당 조건으로 검색된 파티 또는 게시판 정보가 없습니다.");
	                return "partyboard/selectAll";
	            }

	            final List<PartyBoardVO> partyBoardList = list;
	            list3 = list2.stream()
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

	        } else {
	            list = pbservice.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
	            list2 = pservice.selectAll(cpage, pageBlock); // 모든 파티 조회

	            if (list == null || list.isEmpty() || list2 == null || list2.isEmpty()) {
	                model.addAttribute("errorMessage", "해당 조건으로 검색된 파티 또는 게시판 정보가 없습니다.");
	                return "partyboard/selectAll";
	            }

	            final List<PartyVO> partyList = list2;

	            list3 = list.stream()
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
	        }
	        
	        model.addAttribute("list3", list3);
	        model.addAttribute("searchKey", searchKey);
	        model.addAttribute("searchWord", searchWord);
	    } catch (Exception e) {
	        log.error("파티 게시판 검색 중 오류 발생: {}", e.getMessage());
	        model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다. 다시 시도해 주세요.");
	        return "partyboard/selectAll";
	    }

	    log.info("list: {}", list);



	    return "partyboard/selectAll";
	}
	
}
