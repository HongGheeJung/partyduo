package partyDuo.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import partyDuo.com.mapper.ChatMapper;
import partyDuo.com.model.ChatVO;


@Service
public class ChatService {

	@Autowired
	ChatMapper mapper;
	
	@Autowired
    private SqlSession session;
    
    public List<ChatVO> searchListParty(int party_id) {
		return mapper.searchListParty( party_id );
    }
	
	public int insertOK(ChatVO vo) {
		return mapper.insertOK(vo);
	}

	public ChatVO selectOne(ChatVO vo) {
		return mapper.selectOne(vo);
	}

	public int selectLastChat() {
		return mapper.selectLastChat();
	}


	public int deleteOK(ChatVO vo) {
		return mapper.deleteOK(vo);
	}


}
