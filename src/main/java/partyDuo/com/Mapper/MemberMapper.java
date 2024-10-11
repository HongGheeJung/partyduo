package partyDuo.com.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.MemberVO;

@Mapper
public interface MemberMapper {

	int member_insert(MemberVO vo);

	int member_update(MemberVO vo);

	int member_delete(MemberVO vo);

	MemberVO member_selectOne(MemberVO vo);

	List<MemberVO> member_selectAll();

	List<MemberVO> member_searchList(String searchKey, String searchWord);

	int member_login(MemberVO vo);

	int member_apiCheck(MemberVO vo);

	int member_findPwCheck(MemberVO vo);

	int member_findIdCheck(MemberVO vo);
	
}
