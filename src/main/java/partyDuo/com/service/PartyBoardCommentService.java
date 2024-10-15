package partyDuo.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyBoardCommentMapper;

@Slf4j
@Service
public class PartyBoardCommentService {
	
	@Autowired
	PartyBoardCommentMapper pbcmapper;
	
	public int insertOK() {
		log.info("pbc_service_insertOK");
		return 0;
	}
	
	public int updateOK() {
		log.info("pbc_service_updateOK");
		return 0;
	}
	public int deleteOK() {
		log.info("pbc_service_deleteOK");
		return 0;
	}
	
	public void searchList() {
		log.info("pbc_service_searchList");
	}
	
	public void selectOne() {
		log.info("pbc_service_selectOne");
	}
	
}
