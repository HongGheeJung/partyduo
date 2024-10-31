package partyDuo.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.FavoriteMapper;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.model.MemberVO;

@Slf4j
@Service
public class FavoriteService {
	
	@Autowired
	FavoriteMapper mapper;
	
	@Autowired
	MemberService mservice;
	
	public FavoriteVO favorite(String user_id, String character_name){
		FavoriteVO vo=new FavoriteVO();
		MemberVO mvo=new MemberVO();
		mvo.setId(user_id);
		log.info("mvoservice:{}",mservice.member_selectOne(mvo));
		int member_id=mservice.member_selectOne(mvo).getMember_id();
//		int member_id=2;
		vo.setMember_id(member_id);
		vo.setCharacter_name(character_name);
		return vo;
	}
	
	public int favorite_insert(FavoriteVO vo) {
		log.info("favorite_insert./..");
		return mapper.favorite_insert(vo);
	}

	public int favorite_delete(FavoriteVO vo) {
		log.info("favorite_delete...");
		return mapper.favorite_delete(vo);
	}

	public FavoriteVO favorite_selectOne(FavoriteVO vo) {
		log.info("favorite_selectOne...");
		return mapper.favorite_selectOne(vo);
	}

	public List<FavoriteVO> favorite_searchList(String searchKey, int searchNum, int cpage, int pageBlock) {
		// TODO Auto-generated method stub
		log.info("favorite_searchList....");
		int startRow=(cpage-1)*pageBlock;
		if(searchKey.equals("member_id")) {			
			return mapper.favorite_searchListByMember_id(searchNum, startRow, pageBlock);
		}else {
			return mapper.favorite_searchListByCharacter_name(searchNum, startRow, pageBlock);
		}
	}
	
	public int getSearchTotalRows(String searchKey, int searchNum) {
		if(searchKey.equals("member_id")) {
			return mapper.getSearchTotalRowsById(searchNum);
		}
		else {
			return mapper.getSearchTotalRowsByCname(searchNum);
		}
		
	}
}
