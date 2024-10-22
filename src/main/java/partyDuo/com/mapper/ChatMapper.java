package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.ChatVO;

@Mapper
public interface ChatMapper {

	//추상메소드명(예:insertOK)이 sqlMapper_*.xml 문서의 id와 같아야한다.
    int insertOK(ChatVO vo);
	
	List<ChatVO> selectAll();

	ChatVO selectOne(ChatVO vo);

	int updateOK(ChatVO vo);
	int deleteOK(ChatVO vo);

	List<ChatVO> searchListWriter(String searchWord);

	List<ChatVO> searchListParty(int party_id);

	int getTotalRows();


}
