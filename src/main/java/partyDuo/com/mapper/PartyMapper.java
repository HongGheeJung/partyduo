package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyVO;



@Mapper
public interface PartyMapper {
	public List<PartyVO> selectAll();

	public PartyVO selectOne(PartyVO vo);

	public int updateOK(PartyVO vo);
	public int deleteOK(PartyVO vo);

	public List<PartyVO> searchListTitle(String searchWord);

	public List<PartyVO> searchListContent(String searchWord);

	public List<PartyVO> selectAllPageBlock(int startRow, int endRow);

	public int getTotalRows();

	public List<PartyVO> searchListPageBlockTitle(String searchWord, int startRow, int endRow);
	public List<PartyVO> searchListPageBlockContent(String searchWord, int startRow, int endRow);

	public int getSearchTotalRowsTitle(String searchWord);
	public int getSearchTotalRowsContent(String searchWord);

	
	
	

}
