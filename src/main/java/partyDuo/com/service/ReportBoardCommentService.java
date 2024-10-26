package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.ReportBoardCommentMapper; // 수정된 Mapper
import partyDuo.com.model.ReportBoardCommentVO; // 수정된 VO

@Slf4j
@Service
public class ReportBoardCommentService {
	
	@Autowired
	ReportBoardCommentMapper rbcmapper; // 수정된 Mapper 사용
	
	public int insertOK(ReportBoardCommentVO vo) {
		log.info("rbc_service_insertOK");
		return rbcmapper.insertOK(vo);
	}
	
	public int updateOK(ReportBoardCommentVO vo) {
		log.info("rbc_service_updateOK");
		return rbcmapper.updateOK(vo);
	}
	
	public int deleteOK(ReportBoardCommentVO vo) {
		log.info("rbc_service_deleteOK");
		return rbcmapper.deleteOK(vo);
	}
	
	public List<ReportBoardCommentVO> searchListByReportBoardId(String searchWord) {
		log.info("rbc_service_searchListByReportBoardId");
		return rbcmapper.searchListReportBoardId(searchWord); // 인자 타입 수정
	}
	
	public ReportBoardCommentVO selectOne(ReportBoardCommentVO vo) {
		log.info("rbc_service_selectOne");
		return rbcmapper.selectOne(vo);
	}
}
