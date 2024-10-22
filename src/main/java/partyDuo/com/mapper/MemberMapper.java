package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.MemberVO;

@Mapper
public interface MemberMapper {

	int member_insert(MemberVO vo);

	int member_update(MemberVO vo);

	int member_delete(MemberVO vo);

	MemberVO member_selectOne(MemberVO vo);
	
	MemberVO member_selectOneByMember_id(MemberVO vo);

	List<MemberVO> member_selectAll(int startRow, int pageBlock);

	MemberVO member_login(MemberVO vo);

	int member_apiCheck(MemberVO vo);

	String member_findPwCheck(MemberVO vo);

	String member_findIdCheck(MemberVO vo);

	List<MemberVO> member_searchListById(String searchWord, int startRow, int pageBlock);

	List<MemberVO> member_searchListByCharacterName(String searchWord, int startRow, int pageBlock);

	int getTotalRows();

	int getSearchTotalRowsByCname(String searchWord);

	int getSearchTotalRowsById(String searchWord);

	MemberVO apiCheck(MemberVO vo);
	
}
