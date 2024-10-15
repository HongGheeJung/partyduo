package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyBoardVO;



@Mapper
public interface PartyBoardMapper {
	public int insertOK(PartyBoardVO vo);
	
	public int updateOK(PartyBoardVO vo);
	
	public int deleteOK(PartyBoardVO vo);

	public PartyBoardVO selectOne(PartyBoardVO vo);
	
	public List<PartyBoardVO> selectAllPageBlock(int startRow, int endRow);

	public List<PartyBoardVO> searchListPageBlock(String searchKey,String searchWord, int startRow, int endRow);
	
	public int getTotalRows();
	
	public int getSearchTotalRows(String searchKey,String searchWord);
	

	
	
	

}
