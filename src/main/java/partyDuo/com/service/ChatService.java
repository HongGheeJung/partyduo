package partyDuo.com.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.ChatMapper;
import partyDuo.com.model.ChatVO;

@Slf4j
@Service
public class ChatService {

	@Autowired
	ChatMapper mapper;
	
	@Autowired
    private SqlSession session;

    public boolean insertRoomDatas(ChatVO message) {
        mapper = session.getMapper(ChatMapper.class);
        int result = mapper.insertOK(message);
        
        return result == 1;
    }
    
    public List<ChatVO> findMessageById(String roomId) {
        mapper = session.getMapper((ChatMapper.class));
        int roomId2 = Integer.parseInt(roomId);
        List<ChatVO> ChatVO = mapper.searchListParty(roomId2);

        return ChatVO;
    }
    
    public List<ChatVO> searchListParty(int party_id) {
		return mapper.searchListParty( party_id );

}
	
	public int insertOK(ChatVO vo) {
		return mapper.insertOK(vo);
	}

	public List<ChatVO> selectAll() {
		return mapper.selectAll();
	}

	public ChatVO selectOne(ChatVO vo) {
		return mapper.selectOne(vo);
	}

	public int updateOK(ChatVO vo) {
		return mapper.updateOK(vo);
	}

	public int deleteOK(ChatVO vo) {
		return mapper.deleteOK(vo);
	}

	
	
	public List<ChatVO> searchListWriter(String searchWord) {
			return mapper.searchListWriter("%" + searchWord + "%");

	}

	public int getTotalRows() {
		return mapper.getTotalRows();
	}

}
