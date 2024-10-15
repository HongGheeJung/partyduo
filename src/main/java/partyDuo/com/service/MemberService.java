package partyDuo.com.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.MemberMapper;
import partyDuo.com.model.MemberVO;

@Service
@Slf4j
public class MemberService {
	@Autowired
	MemberMapper mapper;

	
	public int member_insert(MemberVO vo) {
		log.info("member_insert()...");
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
	public int member_findPwCheck(MemberVO vo) {
//		return mapper.member_findPwCheck(vo);
		log.info("member_findPwCheck()...");
		return 0;
	}
	public int member_findIDCheck(MemberVO vo){
//		return mapper.member_findIdCheck(vo);
		log.info("member_findIDCheck()...");
		return 0;
	}
}
