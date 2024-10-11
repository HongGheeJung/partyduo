package partyDuo.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyBoardMapper;
import partyDuo.com.mapper.PartyMapper;

@Slf4j
@Service
public class PartyService {
	@Autowired
	PartyMapper pmapper;
	
	public int insertOK() {
		log.info("p_service_insertOK");
		return 0;
	}
	
	public int updateOK() {
		log.info("p_service_updateOK");
		return 0;
	}
	public int deleteOK() {
		log.info("p_service_deleteOK");
		return 0;
	}
	
	public void searchList() {
		log.info("p_service_searchList");
	}
	
	public void selectOne() {
		log.info("p_service_selectOne");
	}
}
