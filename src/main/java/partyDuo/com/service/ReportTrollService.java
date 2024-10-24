package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.ReportTrollMapper;
import partyDuo.com.model.ReportTrollVO;

@Slf4j
@Service
public class ReportTrollService {
	
	@Autowired
	ReportTrollMapper rtmapper;
	
	public int insertOK(ReportTrollVO vo) {
		log.info("rt_service_insertOK");
		return rtmapper.insertOK(vo);
	}
	
	public int updateOK(ReportTrollVO vo) {
		log.info("rt_service_updateOK");
		return rtmapper.updateOK(vo);
	}
	
	public int deleteOK(ReportTrollVO vo) {
		log.info("rt_service_deleteOK");
		return rtmapper.deleteOK(vo);
	}
	
	public ReportTrollVO selectOne(ReportTrollVO vo) {
		log.info("rt_service_selectOne");
		return rtmapper.selectOne(vo);
	}
	
	public List<ReportTrollVO> selectAllPageBlock(int cpage, int pageBlock) {
		log.info("rt_service_selectAll");
		int startRow=(cpage-1)*pageBlock;
		return rtmapper.selectAllPageBlock(startRow, pageBlock);
	}
	
	public List<ReportTrollVO> selectAll(int cpage, int pageBlock) {
		log.info("rt_service_selectAll");
		int startRow=(cpage-1)*pageBlock;
		return rtmapper.selectAll(startRow, pageBlock);
	}
	
	public List<ReportTrollVO> searchListPageBlock(String searchKey,String searchWord,int cpage,int pageBlock) {
		log.info("rt_service_searchList");
		int startRow=(cpage-1)*pageBlock;
		if(searchKey.equals("character_name")) {
			return rtmapper.searchListReportTrollCharacterPageBlock(searchWord, startRow, pageBlock);
		}else {
			return rtmapper.searchListReportTrollWdatePageBlock(searchWord, startRow, pageBlock);
		}
	}
	
	public int getTotalRows() {
		log.info("rt_service_getTotalRows");
		return rtmapper.getTotalRows();
	}
	
	public int getSearchTotalRows(String searchKey,String searchWord) {
		log.info("rt_service_getSearchTotalRows");
	
		if(searchKey.equals("character_name")) {
			return rtmapper.getSearchTotalRowsReportTrollCharacter(searchWord);
		}else {
			return rtmapper.getSearchTotalRowsReportTrollWdate(searchWord);
		}
		
	}
}
