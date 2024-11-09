package partyDuo.com.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.EventMapper;
import partyDuo.com.model.EventVO;

@Slf4j
@Service
public class EventService {

	@Autowired
	EventMapper mapper;
	
	public int insertOK(EventVO vo) {
		return mapper.insertOK(vo);
	}

	public List<EventVO> selectAll() {
		return mapper.selectAll();
	}

	public EventVO selectOne(EventVO vo) {
		return mapper.selectOne(vo);
	}

	public int updateOK(EventVO vo) {
		return mapper.updateOK(vo);
	}

	public int deleteOK(EventVO vo) {
		return mapper.deleteOK(vo);
	}
	
	public List<EventVO> searchListTitle(String searchWord) {
		return mapper.searchListTitle("%" + searchWord + "%");
	}
	public List<EventVO> searchListParty(int party_id){
		
		return mapper.searchListParty(party_id);
	}	


}
