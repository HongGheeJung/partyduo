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
	
	public List<PartyVO> searchList(String searchKey,String searchWord){
		log.info("p_service_searchList");
		
		if (searchKey.equals("party_id")) {
			return pmapper.searchListPartyId("%"+searchWord+"%");
		}else if(searchKey.equals("party_name")) {
			return pmapper.searchListPartyName("%"+searchWord+"%");
		}else if(searchKey.equals("world")) {
			return pmapper.searchListWorld("%"+searchWord+"%");
		}else {
			return pmapper.searchListPartyMaster(searchWord);
		}
	}
	
	public List<PartyVO> searchListPM(String searchKey,String searchWord){
		log.info("p_service_searchListPageBlock");
		
		return pmapper.searchListPartyMaster(searchWord);
		
	}
	
	public PartyVO selectOne(PartyVO vo) {
		log.info("p_service_selectOne");
		return pmapper.selectOne(vo);
	}
	
	public PartyVO selectOnePname(PartyVO vo) {
		log.info("p_service_selectOnePname");
		return pmapper.selectOnePname(vo);
	}
	
	public List<PartyVO> selectAllPageBlock(int cpage, int pageBlock){
		log.info("p_service_selectAll");
		int startRow=(cpage-1)*pageBlock;
		return pmapper.selectAllPageBlock(startRow,pageBlock);
	}
	public List<PartyVO> selectAll(){
		log.info("p_service_selectAll");
		
		return pmapper.selectAll();
	}

	public PartyVO selectOnePM(PartyVO vo) {
		
		return pmapper.selectOnePM(vo);
	}

	public List<PartyVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
		int startRow=(cpage-1)*pageBlock;
		if(searchKey.equals("world")) {
		return pmapper.searchListWorldPageBlock("%"+searchWord+"%",startRow,pageBlock);}
	else if(searchKey.equals("party_name")){ 
		return pmapper.searchListPartyNamePageBlock("%"+searchWord+"%",startRow,pageBlock);
		}else {
			return pmapper.searchListPartyMasterPageBlock("%"+searchWord+"%",startRow,pageBlock);
		}
	
	}
	
	public int getTotalRows() {
		log.info("p_service_getTotalRows");
		return pmapper.getTotalRows();
	}
	
	public int getSearchTotalRows(String searchKey,String searchWord) {
		log.info("p_service_getSearchTotalRows");
		if (searchKey.equals("party_master")) {
			
			return pmapper.getSearchTotalRowsPartyMaster( "%"+searchWord+"%");
		}else if (searchKey.equals("party_name")){
		
			return pmapper.getSearchTotalRowsPartyName("%"+searchWord+"%");
		}else {
			return pmapper.getSearchTotalRowsWorld("%"+searchWord+"%");
		}
	}






}

