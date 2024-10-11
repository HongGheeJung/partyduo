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

	public List<EventVO> searchListId(String searchWord);

	public List<EventVO> searchListName(String searchWord);

	public int getTotalRows();

	public List<EventVO> selectAllPageBlock(int startRow, int pageBlock);

	public int getSearchTotalRowsId(String searchWord);

	public int getSearchTotalRowsName(String searchWord);

	public List<EventVO> searchListPageBlockId(String searchWord, int startRow, int pageBlock);

	public List<EventVO> searchListPageBlockName(String searchWord, int startRow, int pageBlock);
	
	

}
