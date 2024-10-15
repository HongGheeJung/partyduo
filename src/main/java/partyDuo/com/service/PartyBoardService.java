package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyBoardMapper;
import partyDuo.com.model.PartyBoardVO;

@Slf4j
@Service
public class PartyBoardService {
	
	@Autowired
	PartyBoardMapper pbmapper;
	
	public int insertOK(PartyBoardVO vo) {
		log.info("pb_service_insertOK");
		return pbmapper.insertOK(vo);
	}
	
	public int updateOK(PartyBoardVO vo) {
		log.info("pb_service_updateOK");
		return pbmapper.updateOK(vo);
	}
	
	public int deleteOK(PartyBoardVO vo) {
		log.info("pb_service_deleteOK");
		return pbmapper.deleteOK(vo);
	}
	
	public PartyBoardVO selectOne(PartyBoardVO vo) {
		log.info("pb_service_selectOne");
		return pbmapper.selectOne(vo);
	}
	
	public List<PartyBoardVO> selectAllPageBlock(int cpage, int pageBlock) {
		log.info("pb_service_selectAll");
		return pbmapper.selectAllPageBlock(cpage, pageBlock);
	}
	
	public List<PartyBoardVO> searchListPageBlock(String searchKey,String searchWord,int cpage,int pageBlock) {
		log.info("pb_service_searchList");
		return pbmapper.searchListPageBlock(searchKey, searchWord, cpage, pageBlock);
	}
	
	public int getTotalRows() {
		log.info("pb_service_getTotalRows");
		return pbmapper.getTotalRows();
	}
	
	public int getSearchTotalRows(String searchKey,String searchWord) {
		log.info("pb_service_getSearchTotalRows");
		return pbmapper.getSearchTotalRows(searchKey, searchWord);
	}
}
