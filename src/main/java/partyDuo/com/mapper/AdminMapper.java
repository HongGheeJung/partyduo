package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.AdminVO;
import partyDuo.com.model.ChatVO;

@Mapper
public interface AdminMapper {
	public AdminVO selectOne(AdminVO vo);	

	public List<AdminVO> selectAll();
}
