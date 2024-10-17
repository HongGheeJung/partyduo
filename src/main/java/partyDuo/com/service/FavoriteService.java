package partyDuo.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.FavoriteMapper;
import partyDuo.com.model.FavoriteVO;

@Slf4j
@Service
public class FavoriteService {
	
	@Autowired
	FavoriteMapper mapper;
	
	public int favorite_insert(FavoriteVO vo) {
		log.info("favorite_insert./..");
		return mapper.favorite_insert(vo);
	}

	public int favorite_delete(FavoriteVO vo) {
		log.info("favorite_delete...");
		return mapper.favorite_delete(vo);
	}
}
