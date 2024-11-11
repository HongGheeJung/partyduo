package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import partyDuo.com.mapper.EventLikeMapper;
import partyDuo.com.model.EventLikeVO;


@Service
public class EventLikeService {

	@Autowired
	EventLikeMapper mapper;
	
	public int insertOK(EventLikeVO vo) {
		return mapper.insertOK(vo);
	}

	public List<EventLikeVO> selectAll() {
		return mapper.selectAll();
	}
	
	public List<EventLikeVO> selectlist(EventLikeVO vo) {
		return mapper.selectlist(vo);
	}

	public EventLikeVO selectOne(EventLikeVO vo) {
		return mapper.selectOne(vo);
	}
	
	public int selectOneLikecheck(EventLikeVO vo) {
		return mapper.selectOneLikecheck(vo);
	}
	
	public int selectOneLikeCount(EventLikeVO vo) {
		return mapper.selectOneLikeCount(vo);
	}


	public int deleteOK(EventLikeVO vo) {
		return mapper.deleteOK(vo);
	}


}
