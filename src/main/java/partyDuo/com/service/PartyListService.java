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
	
	public List<PartyListVO> searchList(String searchKey,String searchWord){
		log.info("pl_service_searchList()");
			if (searchKey.equals("party_id")) {
				return plmapper.searchListPartyId(searchWord);
					}else{
				return plmapper.searchListMemberId(searchWord);				
			}
		};
	
	public int getTotalPartyListRows(String searchKey,String searchWord) {
		log.info("pl_service_getSearchTotalRows");
		return plmapper.getTotalPartyListRows(searchWord);
		
	};
}
