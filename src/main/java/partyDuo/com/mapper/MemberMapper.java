package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.MemberVO;

@Mapper
public interface MemberMapper {

	public int member_insert(MemberVO vo);

	public int member_update(MemberVO vo);

	public int member_delete(MemberVO vo);

	public MemberVO member_selectOne(MemberVO vo);
	
	public MemberVO member_selectOneByMember_id(MemberVO vo);

	public List<MemberVO> member_selectAll(int startRow, int pageBlock);

	public MemberVO member_login(MemberVO vo);

	public int member_apiCheck(MemberVO vo);

	public String member_findPwCheck(MemberVO vo);

	public String member_findIdCheck(MemberVO vo);

	public List<MemberVO> member_searchListById(String searchWord, int startRow, int pageBlock);

	public List<MemberVO> member_searchListByCharacterName(String searchWord, int startRow, int pageBlock);

	public int getTotalRows();

	public int getSearchTotalRowsByCname(String searchWord);

	public int getSearchTotalRowsById(String searchWord);
	
}
