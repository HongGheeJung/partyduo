package partyDuo.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyBoardMapper;

@Slf4j
@Service
public class PartyBoardService {
	
	@Autowired
	PartyBoardMapper pbmapper;
	
	public int insertOK() {
		log.info("pb_service_insertOK");
		return 0;
	}
	
	public int updateOK() {
		log.info("pb_service_updateOK");
		return 0;
	}
	public int deleteOK() {
		log.info("pb_service_deleteOK");
		return 0;
	}
	
	public void searchList() {
		log.info("pb_service_searchList");
	}
	
	public void selectOne() {
		log.info("pb_service_selectOne");
	}
	public void selectAll() {
		log.info("pb_service_selectAll");
	}
}
