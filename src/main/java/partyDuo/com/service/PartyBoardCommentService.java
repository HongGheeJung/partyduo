package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyBoardCommentMapper;
import partyDuo.com.model.PartyBoardCommentVO;

@Slf4j
@Service
public class PartyBoardCommentService {
	
	@Autowired
	PartyBoardCommentMapper pbcmapper;
	
	public int insertOK(PartyBoardCommentVO vo) {
		log.info("pbc_service_insertOK");
		return pbcmapper.insertOK(vo);
	}
	
	public int updateOK(PartyBoardCommentVO vo) {
		log.info("pbc_service_updateOK");
		return pbcmapper.updateOK(vo);
	}
	public int deleteOK(PartyBoardCommentVO vo) {
		log.info("pbc_service_deleteOK");
		return pbcmapper.deleteOK(vo);
	}
	
	public List<PartyBoardCommentVO> searchListPartyBoardId(String searchWord) {
		log.info("pbc_service_searchListPartyBoardId");
		return pbcmapper.searchListPartyBoardId(searchWord);
	}
	
	public PartyBoardCommentVO selectOne(PartyBoardCommentVO vo) {
		log.info("pbc_service_selectOne");
		return pbcmapper.selectOne(vo);
	}
	
}
