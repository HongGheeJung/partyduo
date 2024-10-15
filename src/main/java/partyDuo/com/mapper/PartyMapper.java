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
	
	public List<PartyVO> searchList(String searchKey, String searchWord);
	
	public List<PartyVO> searchListPageBlock( String searchKey,String searchWord,int startRow, int pageBlock);

	public List<PartyVO> selectAll(int startRow, int pageBlock);
}
