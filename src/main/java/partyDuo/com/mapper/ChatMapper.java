package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.ChatVO;

@Mapper
public interface ChatMapper {

	public int insertOK(ChatVO vo);
	
	public ChatVO selectOne(ChatVO vo);
	
	public int selectLastChat();
	
	public int deleteOK(ChatVO vo);

	public List<ChatVO> searchListParty(int party_id);




}
