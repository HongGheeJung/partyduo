package partyDuo.com.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
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

	public List<ChatVO> searchList(String searchKey, String searchWord) {
		if (searchKey.equals("month")) {
			int searchWord2 = Integer.parseInt(searchWord);
			return mapper.searchListMonth( searchWord2 );
		} else {
			
			return mapper.searchListTitle("%" + searchWord + "%");
		}

	}

	public int getTotalRows() {
		return mapper.getTotalRows();
	}

}
