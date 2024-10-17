package partyDuo.com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.FavoriteVO;

@Mapper
public interface FavoriteMapper {

	int favorite_insert(FavoriteVO vo);
	int favorite_delete(FavoriteVO vo);
	FavoriteVO favorite_selectOne(FavoriteVO vo);
	List<FavoriteVO> favorite_searchListByMember_id(int searchNum);
	List<FavoriteVO> favorite_searchListByCharacter_name(int searchNum);
	
}
