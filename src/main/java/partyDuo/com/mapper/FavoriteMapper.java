package partyDuo.com.mapper;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.FavoriteVO;

@Mapper
public interface FavoriteMapper {

	int favorite_insert(FavoriteVO vo);
	int favorite_delete(FavoriteVO vo);
	
}
