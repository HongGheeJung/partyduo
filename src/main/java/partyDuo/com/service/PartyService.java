package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyBoardMapper;
import partyDuo.com.mapper.PartyMapper;
import partyDuo.com.model.PartyVO;

@Slf4j
@Service
public class PartyService {
	@Autowired
	PartyMapper pmapper;
	
	public int insertOK(PartyVO vo) {
		log.info("p_service_insertOK");
		return pmapper.insertOK(vo);
	}
	
	public int updateOK(PartyVO vo) {
		log.info("p_service_updateOK");
		return pmapper.updateOK(vo);
	}
	public int deleteOK(PartyVO vo) {
		log.info("p_service_deleteOK");
		return pmapper.deleteOK(vo);
	}
	
	public List<PartyVO> searchList(String searchWord,String searchKey) {
		log.info("p_service_searchList");
		return pmapper.searchList(searchWord,searchKey);
	}
	
	public List<PartyVO> searchListPageBlock(String searchWord,String searchKey,int startRow, int endRow){
		log.info("p_service_searchListPageBlock");
		return pmapper.searchListPageBlock(searchWord, searchKey,startRow,endRow);
	}
	
	public PartyVO selectOne(PartyVO vo) {
		log.info("p_service_selectOne");
		return pmapper.selectOne(vo);
	}
	
	public int getTotalRows() {
		return pmapper.getTotalRows();
	}

	public int getSearchTotalRows(String searchWord,String searchKey) {
		return pmapper.getSearchTotalRows(searchWord, searchKey);
	}
}
