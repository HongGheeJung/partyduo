package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.ReportBoardMapper; // 수정된 Mapper
import partyDuo.com.model.ReportBoardVO; // 수정된 VO

@Slf4j
@Service
public class ReportBoardService {
	
	@Autowired
	ReportBoardMapper rbmapper;
	
	public int insertOK(ReportBoardVO vo) {
		log.info("rb_service_insertOK");
		return rbmapper.insertOK(vo);
	}
	
	public int updateOK(ReportBoardVO vo) {
		log.info("rb_service_updateOK");
		return rbmapper.updateOK(vo);
	}
	
	public int deleteOK(ReportBoardVO vo) {
		log.info("rb_service_deleteOK");
		return rbmapper.deleteOK(vo);
	}
	
	public ReportBoardVO selectOne(ReportBoardVO vo) {
		log.info("rb_service_selectOne");
		return rbmapper.selectOne(vo);
	}
	
	public List<ReportBoardVO> selectAllPageBlock(int cpage, int pageBlock) {
		log.info("rb_service_selectAll");
		int startRow = (cpage - 1) * pageBlock;
		return rbmapper.selectAllPageBlock(startRow, pageBlock);
	}
	
	public List<ReportBoardVO> selectAll() {
		log.info("rb_service_selectAll");
		return rbmapper.selectAll();
	}
	
	public List<ReportBoardVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
		log.info("rb_service_searchList");
		int startRow = (cpage - 1) * pageBlock;

		if (searchKey.equals("title")) {
			log.info("title");
			return rbmapper.searchListTitlePageBlock("%" + searchWord + "%", startRow, pageBlock);
		} else if (searchKey.equals("writer")) {
			log.info("writer");
			return rbmapper.searchListWriterPageBlock("%" + searchWord + "%", startRow, pageBlock);
		} else { // 기본적으로 작성일로 검색
			log.info("wdate");
			return rbmapper.searchListWdatePageBlock("%" + searchWord + "%", startRow, pageBlock);
		}
	}
	
	public int getTotalRows() {
		log.info("rb_service_getTotalRows");
		return rbmapper.getTotalRows();
	}
	
	public int getSearchTotalRows(String searchKey, String searchWord) {
		log.info("rb_service_getSearchTotalRows");

		if (searchKey.equals("title")) {
			return rbmapper.getSearchTotalRowsTitle("%" + searchWord + "%");
		} else if (searchKey.equals("writer")) {
			return rbmapper.getSearchTotalRowsWriter("%" + searchWord + "%");
		} else { // 기본적으로 작성일로 검색
			return rbmapper.getSearchTotalRowsWdate("%" + searchWord + "%"); 
		}
	}
}
