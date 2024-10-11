package partyDuo.com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.Mapper.MemberMapper;
import partyDuo.com.model.MemberVO;

@Service
@Slf4j
public class MemberService {
	@Autowired
	MemberMapper mapper;
	
	public int member_insert(MemberVO vo) {
//		return mapper.member_insert(vo);
		log.info("member_insert()...");
		return 0;
	}
	public int member_update(MemberVO vo) {
//		return mapper.member_update(vo);
		log.info("member_update()...");
		return 0;
	}
	public int member_delete(MemberVO vo) {
//		return mapper.member_delete(vo);
		log.info("member_delete()...");
		return 0;
	}
	public MemberVO member_selectOne(MemberVO vo) {
		log.info("member_selectOne()...");
//		return mapper.member_selectOne(vo);
		MemberVO vo2=new MemberVO();
		return vo2;
	}
	public List<MemberVO> member_selectAll(){
//		return mapper.member_selectAll();
		log.info("member_selectAll()...");
		return null;
	}
	public List<MemberVO> member_searchList(String searchKey, String searchWord){
//		return mapper.member_searchList(searchKey, searchWord);
		log.info("member_searchList()...");
		return null;
	}
	public int member_login(MemberVO vo) {
//		return mapper.member_login(vo);
		log.info("member_login()...");
		return 0;
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
