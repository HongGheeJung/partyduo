package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.ChatVO;

@Mapper
public interface ChatMapper {

	//추상메소드명(예:insertOK)이 sqlMapper_*.xml 문서의 id와 같아야한다.
	public int insertOK(ChatVO vo);
	
	public List<ChatVO> selectAll();

	public ChatVO selectOne(ChatVO vo);

	public int updateOK(ChatVO vo);
	public int deleteOK(ChatVO vo);

	public List<ChatVO> searchListMonth(int searchWord2);

	public List<ChatVO> searchListTitle(String searchWord);

	public int getTotalRows();


}
