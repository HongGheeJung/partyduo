package partyDuo.com.service;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.spiralmoon.maplestory.api.MapleStoryApi;
import dev.spiralmoon.maplestory.api.MapleStoryApiException;
import dev.spiralmoon.maplestory.api.dto.character.*;
import dev.spiralmoon.maplestory.api.dto.ranking.UnionRankingResponseDTO;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.AdminMapper;
import partyDuo.com.model.AdminVO;

@Slf4j
@Service
public class AdminService {

	@Autowired
	AdminMapper admapper;
	
	public AdminVO selectOne(AdminVO vo) {
		
		return admapper.selectOne(vo);
	}
	
	public List<AdminVO> selectAll() {
		
		return admapper.selectAll();
	}
}
