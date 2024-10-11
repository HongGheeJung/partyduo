package partyDuo.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyListMapper;

@Slf4j
@Service
public class PartyListService {
	@Autowired
	PartyListMapper plmapper;
	
	public int insertOK() {
		log.info("pl_service_insertOK");
		return 0;
	}
	
	public int updateOK() {
		log.info("pl_service_updateOK");
		return 0;
	}
	public int deleteOK() {
		log.info("pl_service_deleteOK");
		return 0;
	}
	
	public void searchList() {
		log.info("pl_service_searchList");
	}
	
	public void selectOne() {
		log.info("pl_service_selectOne");
	}
	
}
