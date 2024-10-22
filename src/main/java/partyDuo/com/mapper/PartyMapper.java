package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyVO;



@Mapper
public interface PartyMapper {
	int insertOK(PartyVO vo);
	
	int updateOK(PartyVO vo);
	
	int deleteOK(PartyVO vo);
	
	PartyVO selectOne(PartyVO vo);
	PartyVO selectOnePname(PartyVO vo);
	
	List<PartyVO> searchListPartyId(String searchWord);
	List<PartyVO> searchListPartyMaster(String searchWord);
	List<PartyVO> searchListPartyName(String searchWord);

	List<PartyVO> selectAll(int startRow, int pageBlock);

	PartyVO selectOnePM(PartyVO vo);
}
