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
	
	public List<PartyVO> searchListPartyId(String searchWord);
	public List<PartyVO> searchListPartyMaster(String searchWord);
	public List<PartyVO> searchListPartyName(String searchWord);

	public List<PartyVO> selectAll(int startRow, int pageBlock);
}
