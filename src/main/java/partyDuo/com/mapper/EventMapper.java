package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.EventVO;

@Mapper
public interface EventMapper {

	//추상메소드명(예:insertOK)이 sqlMapper_*.xml 문서의 id와 같아야한다.
    int insertOK(EventVO vo);
	
	List<EventVO> selectAll();

	EventVO selectOne(EventVO vo);

	int updateOK(EventVO vo);
	int deleteOK(EventVO vo);

	List<EventVO> searchListPartyMonth(int party_id, int month, int year);

	List<EventVO> searchListTitle(String searchWord);

	int getTotalRows();


}
