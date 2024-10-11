package partyDuo.com.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.EventMapper;
import partyDuo.com.model.EventVO;

@Slf4j
@Service
public class EventService {

	@Autowired
	EventMapper mapper;
	
	public int insertOK(EventVO vo) {
		return mapper.insertOK(vo);
	}

	public List<EventVO> selectAll() {
		return mapper.selectAll();
	}

	public EventVO selectOne(EventVO vo) {
		return mapper.selectOne(vo);
	}

	public int updateOK(EventVO vo) {
		return mapper.updateOK(vo);
	}

	public int deleteOK(EventVO vo) {
		return mapper.deleteOK(vo);
	}

	public List<EventVO> searchList(String searchKey, String searchWord) {
		if (searchKey.equals("id")) {
			return mapper.searchListId("%" + searchWord + "%");
		} else {
			return mapper.searchListName("%" + searchWord + "%");
		}

	}

	public int getTotalRows() {
		return mapper.getTotalRows();
	}

	public List<EventVO> selectAllPageBlock(int cpage, int pageBlock) {
		// MySql 인경우 limit 시작행을얻어내는 알고리즘이 필요하다.
		// 예:1페이지(0,pageBlock),2페이지(5,pageBlock),3페이지(10,pageBlock)
		int startRow = (cpage - 1) * pageBlock ;
		log.info("startRow:{}", startRow);
		log.info("pageBlock:{}", pageBlock);

		return mapper.selectAllPageBlock(startRow, pageBlock);
	}

	public int getSearchTotalRows(String searchKey, String searchWord) {
		if (searchKey.equals("id")) {
			return mapper.getSearchTotalRowsId("%" + searchWord + "%");
		} else {
			return mapper.getSearchTotalRowsName("%" + searchWord + "%");
		}
	}

	public List<EventVO> searchListPageBlock(String searchKey, String searchWord, 
			int cpage, int pageBlock) {
		int startRow = (cpage - 1) * pageBlock ;//mysql은 리미트 처리시 0행부터 시작 +1안해도됨
		log.info("startRow:{}", startRow);

		if (searchKey.equals("id")) {
			return mapper.searchListPageBlockId("%" + searchWord + "%",startRow,pageBlock);
		} else {
			return mapper.searchListPageBlockName("%" + searchWord + "%",startRow,pageBlock);
		}
	}

}
