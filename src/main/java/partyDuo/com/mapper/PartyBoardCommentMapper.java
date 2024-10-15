package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.PartyBoardCommentVO;

@Mapper
public interface PartyBoardCommentMapper {
	public int insertOK(PartyBoardCommentVO vo);
	
	public List<PartyBoardCommentVO> selectAll();

	public PartyBoardCommentVO selectOne(PartyBoardCommentVO vo);

	public int updateOK(PartyBoardCommentVO vo);
	public int deleteOK(PartyBoardCommentVO vo);

	public List<PartyBoardCommentVO> searchListTitle(String searchWord);

	public List<PartyBoardCommentVO> searchListContent(String searchWord);

	public List<PartyBoardCommentVO> selectAllPageBlock(int startRow, int endRow);

	public int getTotalRows();

	public List<PartyBoardCommentVO> searchListPageBlockTitle(String searchWord, int startRow, int endRow);
	public List<PartyBoardCommentVO> searchListPageBlockContent(String searchWord, int startRow, int endRow);

	public int getSearchTotalRowsTitle(String searchWord);
	public int getSearchTotalRowsContent(String searchWord);

	
	
	

}
