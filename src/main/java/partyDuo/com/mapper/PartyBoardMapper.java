package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyBoardVO;



@Mapper
public interface PartyBoardMapper {
	int insertOK(PartyBoardVO vo);
	
	int updateOK(PartyBoardVO vo);
	
	int deleteOK(PartyBoardVO vo);

	PartyBoardVO selectOne(PartyBoardVO vo);
	
	List<PartyBoardVO> selectAllPageBlock(int startRow, int pageBlock);
	List<PartyBoardVO> selectAll(int startRow, int pageBlock);

	List<PartyBoardVO> searchListBossPageBlock(String searchWord, int startRow, int pageBlock);
	List<PartyBoardVO> searchListPartyBoardIdPageBlock(String searchWord, int startRow, int pageBlock);
	List<PartyBoardVO> searchListPartyBoardWriterPageBlock(String searchWord, int startRow, int pageBlock);
	List<PartyBoardVO> searchListPartyBoardWdatePageBlock(String searchWord, int startRow, int pageBlock);
	
	int getTotalRows();
	int getSearchTotalRowsBoardId(String searchWord);
	int getSearchTotalRowsBoardWriter(String searchWord);
	int getSearchTotalRowsBoardWdate(String searchWord);
	int getSearchTotalRowsBoss(String searchWord);
	

	
	
	

}
