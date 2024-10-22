package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyBoardCommentVO;

@Mapper
public interface PartyBoardCommentMapper {
	int insertOK(PartyBoardCommentVO vo);
	
	int updateOK(PartyBoardCommentVO vo);
	
	int deleteOK(PartyBoardCommentVO vo);
	
	PartyBoardCommentVO selectOne(PartyBoardCommentVO vo);

	List<PartyBoardCommentVO> searchListPartyBoardId(String searchWord);
}
