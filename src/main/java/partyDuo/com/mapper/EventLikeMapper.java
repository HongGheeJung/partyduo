package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.EventLikeVO;

@Mapper
public interface EventLikeMapper {

	//추상메소드명(예:insertOK)이 sqlMapper_*.xml 문서의 id와 같아야한다.
    int insertOK(EventLikeVO vo);
	
	List<EventLikeVO> selectAll();

	EventLikeVO selectOne(EventLikeVO vo);
	
	int selectOneLikecheck(EventLikeVO vo);

	int deleteOK(EventLikeVO vo);
	
	int selectOneLikeCount(EventLikeVO vo);
	


	

}
