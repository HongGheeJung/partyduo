package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyBoardVO;



@Mapper
public interface PartyBoardMapper {
public int insertOK(PartyBoardVO vo);
	
	public List<PartyBoardVO> selectAll();

	public PartyBoardVO selectOne(PartyBoardVO vo);

	public int updateOK(PartyBoardVO vo);
	public int deleteOK(PartyBoardVO vo);

	public List<PartyBoardVO> searchListTitle(String searchWord);

	public List<PartyBoardVO> searchListContent(String searchWord);

	public List<PartyBoardVO> selectAllPageBlock(int startRow, int endRow);

	public int getTotalRows();

	public List<PartyBoardVO> searchListPageBlockTitle(String searchWord, int startRow, int endRow);
	public List<PartyBoardVO> searchListPageBlockContent(String searchWord, int startRow, int endRow);

	public int getSearchTotalRowsTitle(String searchWord);
	public int getSearchTotalRowsContent(String searchWord);

	
	
	

}
