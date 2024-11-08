package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyBoardVO;



@Mapper
public interface PartyBoardMapper {
	public int insertOK(PartyBoardVO vo);
	
	public int updateOK(PartyBoardVO vo);
	public int masterUpdate(PartyBoardVO vo);
	
	public int deleteOK(PartyBoardVO vo);

	public PartyBoardVO selectOne(PartyBoardVO vo);
	
	public List<PartyBoardVO> selectAllPageBlock(int startRow, int pageBlock);
	public List<PartyBoardVO> selectAll();

	public List<PartyBoardVO> searchListBossPageBlock(String searchWord, int startRow, int pageBlock);
	public List<PartyBoardVO> searchListPartyBoardIdPageBlock(String searchWord, int startRow, int pageBlock);
	public List<PartyBoardVO> searchListPartyBoardWriterPageBlock(String searchWord, int startRow, int pageBlock);
	public List<PartyBoardVO> searchListPartyBoardWdatePageBlock(String searchWord, int startRow, int pageBlock);
	
	public int getTotalRows();
	public int getSearchTotalRowsId(String searchWord);
	public int getSearchTotalRowsBoardWriter(String searchWord);
	public int getSearchTotalRowsBoardWdate(String searchWord);
	public int getSearchTotalRowsBoss(String searchWord);
	

	
	
	

}
