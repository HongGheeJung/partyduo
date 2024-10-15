package partyDuo.com.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.RandomAuth;
import partyDuo.com.mapper.MemberMapper;
import partyDuo.com.model.MemberVO;

@Service
@Slf4j
public class MemberService {
	@Autowired
	MemberMapper mapper;
	
	@Autowired
	CharacterService service;

	
	public int member_insert(MemberVO vo) {
		log.info("member_insert()...");
//		vo.setCharacter_name(service.bonCharacter(vo.getApikey())); api 인증 로직 테스트할때는 주석 풀고 해주세용
		return mapper.member_insert(vo);
//		return 0;
	}
	public int member_update(MemberVO vo) {
		log.info("member_update()...");
		return mapper.member_update(vo);
//		return 0;
	}
	public int member_delete(MemberVO vo) {
		log.info("member_delete()...");
		return mapper.member_delete(vo);
//		return 0;
	}
	public MemberVO member_selectOne(MemberVO vo) {
		log.info("member_selectOne()...");
		return mapper.member_selectOne(vo);
//		return vo2;
	}
	public List<MemberVO> member_selectAll(int cpage, int pageBlock){
		log.info("member_selectAll()...");
		int startRow=(cpage-1)*pageBlock;
		return mapper.member_selectAll(startRow, pageBlock);
//		return null;
	}
	public List<MemberVO> member_searchList(String searchKey, String searchWord, int cpage, int pageBlock){
		log.info("member_searchList()...");
		int startRow=(cpage-1)*pageBlock;
		if(searchKey.equals("id")) {
			return mapper.member_searchListById("%"+searchWord+"%", startRow, pageBlock);
		}else {
			return mapper.member_searchListByCharacterName("%"+searchWord+"%", startRow, pageBlock);
		}
	}
	public MemberVO member_login(MemberVO vo) {
		log.info("member_login()...");
		return mapper.member_login(vo);
//		return null;
	}
	public int member_apiCheck(MemberVO vo) {
//		return mapper.member_apiCheck(vo);
		log.info("member_apiCheck()...");
		return 0;
	}
	public String member_findPwCheck(MemberVO vo) {
		log.info("member_findPwCheck()...");
		vo.setPw(RandomAuth.authGenerate(10));
		log.info("vo: {}", vo);
		int result=mapper.member_update(vo);
		if(result==0) {
			return null;
		}else {
			return mapper.member_findPwCheck(vo);
		}
//		return vo.getPw();
	}
	public String member_findIDCheck(MemberVO vo){
		log.info("member_findIDCheck()...");
		return mapper.member_findIdCheck(vo);
//		return null;
	}
	public int getTotalRows() {
		// TODO Auto-generated method stub
		return mapper.getTotalRows();
	}
	public int getSearchTotalRows(String searchKey, String searchWord) {
		if(searchKey.equals("id")) {
			return mapper.getSearchTotalRowsById("%"+searchWord+"%");
		}
		else {
			return mapper.getSearchTotalRowsByCname("%"+searchWord+"%");
		}
		
	}
}
