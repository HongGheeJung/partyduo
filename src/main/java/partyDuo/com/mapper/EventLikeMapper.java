package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.EventLikeVO;

@Mapper
public interface EventLikeMapper {

	//추상메소드명(예:insertOK)이 sqlMapper_*.xml 문서의 id와 같아야한다.
	public int insertOK(EventLikeVO vo);
	
	public List<EventLikeVO> selectAll();

	public EventLikeVO selectOne(EventLikeVO vo);
	
	public int selectOneLikecheck(EventLikeVO vo);

	public int deleteOK(EventLikeVO vo);
	
	public int selectOneLikeCount(EventLikeVO vo);
	


	

}
