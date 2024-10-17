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
	public int getTotalRows();
	public int getSearchTotalRows(String searchKey,String searchWord);
	public List<PartyListVO> searchListPageBlock(String searchKey,String searchWord, int startRow, int pageBlock);
	public List<PartyListVO> searchList(String searchKey,String searchWord);


	
	
	

}
