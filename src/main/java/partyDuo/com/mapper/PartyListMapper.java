package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.MyPartyVO;
import partyDuo.com.model.PartyListVO;



@Mapper
public interface PartyListMapper {
	int insertOK(PartyListVO vo);
	int insertOKPartyMaster(PartyListVO vo);
	
	int updateOK(PartyListVO vo);
	int deleteOK(PartyListVO vo);
	
	PartyListVO selectOne(PartyListVO vo);
	
	int getTotalPartyListRows(String searchWord);
	
	List<PartyListVO> searchListPartyId(String searchWord);
	List<PartyListVO> searchListMemberId(String searchWord);
	List<PartyListVO> searchListJoinMemberId(String searchWord);
	List<MyPartyVO> searchMyParty(String searchWord, int startRow, int pageBlock);


	
	
	

}
