package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyListVO;



@Mapper
public interface PartyListMapper {
	public List<PartyListVO> selectAll();

	public PartyListVO selectOne(PartyListVO vo);

	public int updateOK(PartyListVO vo);
	public int deleteOK(PartyListVO vo);

	public List<PartyListVO> searchListTitle(String searchWord);

	public List<PartyListVO> searchListContent(String searchWord);

	public List<PartyListVO> selectAllPageBlock(int startRow, int endRow);

	public int getTotalRows();

	public List<PartyListVO> searchListPageBlockTitle(String searchWord, int startRow, int endRow);
	public List<PartyListVO> searchListPageBlockContent(String searchWord, int startRow, int endRow);

	public int getSearchTotalRowsTitle(String searchWord);
	public int getSearchTotalRowsContent(String searchWord);

	
	
	

}
