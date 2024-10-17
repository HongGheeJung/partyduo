package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.EventVO;

@Mapper
public interface EventMapper {

	//추상메소드명(예:insertOK)이 sqlMapper_*.xml 문서의 id와 같아야한다.
	public int insertOK(EventVO vo);
	
	public List<EventVO> selectAll();

	public EventVO selectOne(EventVO vo);

	public int updateOK(EventVO vo);
	public int deleteOK(EventVO vo);

	public List<EventVO> searchListPartyMonth(int party_id, int searchWord);

	public List<EventVO> searchListTitle(String searchWord);

	public int getTotalRows();


}
