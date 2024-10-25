package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.NoticeVO;
import partyDuo.com.model.PartyBoardVO;



@Mapper
public interface NoticeMapper {
	public int insertOK(NoticeVO vo);
	
	public int updateOK(NoticeVO vo);
	
	public int deleteOK(NoticeVO vo);

	public NoticeVO selectOne(NoticeVO vo);
	
	public List<NoticeVO> selectAllPageBlock(int startRow, int pageBlock);
	public List<NoticeVO> selectAll();

	public List<NoticeVO> searchListNoticeTitle(String searchWord, int startRow, int pageBlock);
	public List<NoticeVO> searchListNoticeWriter(String searchWord, int startRow, int pageBlock);
	
	public int getTotalRows();
	public int getSearchTotalRowsNoticeTitle(String searchWord);
	public int getSearchTotalRowsNoticeWriter(String searchWord);
	

	
	
	

}
