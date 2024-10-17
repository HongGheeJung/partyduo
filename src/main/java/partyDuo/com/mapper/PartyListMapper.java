package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyListVO;



@Mapper
public interface PartyListMapper {
	public int insertOK(PartyListVO vo);
	public int updateOK(PartyListVO vo);
	public int deleteOK(PartyListVO vo);
	
	public PartyListVO selectOne(PartyListVO vo);
	
	public int getTotalPartyListRows(String searchWord);
	
	public List<PartyListVO> searchListPartyId(String searchWord);
	public List<PartyListVO> searchListMemberId(String searchWord);


	
	
	

}
