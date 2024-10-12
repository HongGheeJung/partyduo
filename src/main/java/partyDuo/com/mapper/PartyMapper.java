package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyVO;



@Mapper
public interface PartyMapper {
	public List<PartyVO> selectAll();

	public PartyVO selectOne(PartyVO vo);
	
	public int insertOK(PartyVO vo);
	public int updateOK(PartyVO vo);
	public int deleteOK(PartyVO vo);

	public int getTotalRows();

	public int getSearchTotalRowsTitle(String searchWord);
	public int getSearchTotalRowsContent(String searchWord);

	public List<PartyVO> searchList(String searchWord, String searchKey);
	public List<PartyVO> searchListPageBlock(String searchWord, String searchKey,int startRow, int endRow);

	
	
	

}
