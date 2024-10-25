package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyBoardCommentVO;

@Mapper
public interface ReportBoardCommentMapper {
	public int insertOK(PartyBoardCommentVO vo);
	
	public int updateOK(PartyBoardCommentVO vo);
	
	public int deleteOK(PartyBoardCommentVO vo);
	
	public PartyBoardCommentVO selectOne(PartyBoardCommentVO vo);

	public List<PartyBoardCommentVO> searchListPartyBoardId(String searchWord);
}
