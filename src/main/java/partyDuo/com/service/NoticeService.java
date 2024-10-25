package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.NoticeMapper;
import partyDuo.com.mapper.PartyBoardMapper;
import partyDuo.com.model.NoticeVO;

@Slf4j
@Service
public class NoticeService {
	
	@Autowired
	NoticeMapper nmapper;
	
	public int insertOK(NoticeVO vo) {
		log.info("n_service_insertOK");
		return nmapper.insertOK(vo);
	}
	
	public int updateOK(NoticeVO vo) {
		log.info("n_service_updateOK");
		return nmapper.updateOK(vo);
	}
	
	public int deleteOK(NoticeVO vo) {
		log.info("n_service_deleteOK");
		return nmapper.deleteOK(vo);
	}
	
	public NoticeVO selectOne(NoticeVO vo) {
		log.info("n_service_selectOne");
		return nmapper.selectOne(vo);
	}
	
	public List<NoticeVO> selectAllPageBlock(int cpage, int pageBlock) {
		log.info("n_service_selectAll");
		int startRow=(cpage-1)*pageBlock;
		return nmapper.selectAllPageBlock(startRow, pageBlock);
	}
	
	public List<NoticeVO> selectAll() {
		log.info("n_service_selectAll");
		return nmapper.selectAll();
	}
	
	public List<NoticeVO> searchListPageBlock(String searchKey,String searchWord,int cpage,int pageBlock) {
		log.info("n_service_searchList");
		int startRow=(cpage-1)*pageBlock;
		if (searchKey.equals("title")) {
		
			return nmapper.searchListNoticeTitle( "%"+searchWord+"%", startRow, pageBlock);
		}else{
		
			return nmapper.searchListNoticeWriter("%"+searchWord+"%", startRow, pageBlock);
		}
	}
	
	public int getTotalRows() {
		log.info("n_service_getTotalRows");
		return nmapper.getTotalRows();
	}
	
	public int getSearchTotalRows(String searchKey,String searchWord) {
		log.info("n_service_getSearchTotalRows");
		if (searchKey.equals("title")) {
			
			return nmapper.getSearchTotalRowsNoticeTitle( "%"+searchWord+"%");
		}else{
		
			return nmapper.getSearchTotalRowsNoticeWriter("%"+searchWord+"%");
		}
	}
	
}
