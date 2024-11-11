package partyDuo.com.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import partyDuo.com.mapper.EventMapper;
import partyDuo.com.model.EventVO;


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
