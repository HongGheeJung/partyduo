package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyListMapper;
import partyDuo.com.model.PartyListVO;

@Slf4j
@Service
public class PartyListService {
	@Autowired
	PartyListMapper plmapper;
	
	public int insertOK(PartyListVO vo) {
		log.info("pl_service_insertOK");
		return plmapper.insertOK(vo);
		}
	
	public int updateOK(PartyListVO vo) {
		log.info("pl_service_updateOK");
		return plmapper.updateOK(vo) ;
	}
	
	public int deleteOK(PartyListVO vo) {
		log.info("pl_service_deleteOK");
		return plmapper.deleteOK(vo);
	}
	
	public PartyListVO selectOne(PartyListVO vo) {
		log.info("pl_service_selectOne");
		return plmapper.selectOne(vo);
	}
	
	public int getTotalRows() {
		log.info("pl_service_getTotalRows()");
		return plmapper.getTotalRows();		
	};

	public List<PartyListVO> searchListPageBlock(String searchKey,String searchWord, int startRow, int pageBlock){
		log.info("pl_service_searchListPageBlock()");
		return plmapper.searchListPageBlock(searchKey, searchWord, startRow, pageBlock);
		
	};
	
	public List<PartyListVO> searchList(String searchKey,String searchWord){
		log.info("pl_service_searchList()");
		return plmapper.searchList(searchKey, searchWord);	
	};
	
	public int getSearchTotalRows(String searchKey,String searchWord) {
		log.info("pl_service_getSearchTotalRows");
		return plmapper.getSearchTotalRows(searchKey, searchWord);
		
	};
}
