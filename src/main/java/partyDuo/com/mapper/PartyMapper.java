package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyVO;



@Mapper
public interface PartyMapper {
	public int insertOK(PartyVO vo);
	
	public int updateOK(PartyVO vo);
	
	public int deleteOK(PartyVO vo);
	
	public PartyVO selectOne(PartyVO vo);
	public PartyVO selectOnePname(PartyVO vo);

	public List<PartyVO> searchListPartyMaster(String searchWord);
	public List<PartyVO> searchListPartyId(String searchWord);
	public List<PartyVO> searchListPartyName(String searchWord);
	public List<PartyVO> searchListWorld(String searchWord);

	public List<PartyVO> selectAllPageBlock(int startRow, int pageBlock);
	public List<PartyVO> selectAll();

	public PartyVO selectOnePM(PartyVO vo);

	public List<PartyVO> searchListWorldPageBlock(String searchWord, int startRow, int pageBlock);
	public List<PartyVO> searchListPartyMasterPageBlock(String searchWord, int startRow, int pageBlock);
	public List<PartyVO> searchListPartyNamePageBlock(String searchWord, int startRow, int pageBlock);

	public int getTotalRows();
	public int getSearchTotalRowsWorld(String searchWord);
	public int getSearchTotalRowsPartyMaster(String searchWord);
	public int getSearchTotalRowsPartyName(String searchWord);


}
