package partyDuo.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.mapper.PartyBoardMapper;
import partyDuo.com.model.PartyBoardVO;

@Slf4j
@Service
public class PartyBoardService {
	
	@Autowired
	PartyBoardMapper pbmapper;
	
	public int insertOK(PartyBoardVO vo) {
		log.info("pb_service_insertOK");
		return pbmapper.insertOK(vo);
	}
	
	public int updateOK(PartyBoardVO vo) {
		log.info("pb_service_updateOK");
		return pbmapper.updateOK(vo);
	}
	
	public int deleteOK(PartyBoardVO vo) {
		log.info("pb_service_deleteOK");
		return pbmapper.deleteOK(vo);
	}
	
	public PartyBoardVO selectOne(PartyBoardVO vo) {
		log.info("pb_service_selectOne");
		return pbmapper.selectOne(vo);
	}
	
	public List<PartyBoardVO> selectAllPageBlock(int cpage, int pageBlock) {
		log.info("pb_service_selectAll");
		int startRow=(cpage-1)*pageBlock;
		return pbmapper.selectAllPageBlock(startRow, pageBlock);
	}
	
	public List<PartyBoardVO> selectAll() {
		log.info("pb_service_selectAll");
		
		return pbmapper.selectAll();
	}
	
	public List<PartyBoardVO> searchListPageBlock(String searchKey,String searchWord,int cpage,int pageBlock) {
		log.info("pb_service_searchList");
		int startRow=(cpage-1)*pageBlock;
		if (searchKey.equals("boss")) {
			log.info("pb_Boss");
		return pbmapper.searchListBossPageBlock( "%"+searchWord+"%", startRow, pageBlock);
		}else if(searchKey.equals("party_board_id")) {
			log.info("pb_id");
			return pbmapper.searchListPartyBoardIdPageBlock(searchWord, startRow, pageBlock);
		}else if(searchKey.equals("party_board_writer")) {
			log.info("pb_writer");
			return pbmapper.searchListPartyBoardWriterPageBlock("%"+searchWord+"%", startRow, pageBlock);
		}else {
			log.info("pb_wdate");
			return pbmapper.searchListPartyBoardWdatePageBlock("%"+searchWord+"%", startRow, pageBlock);
		}
	}
	
	public int getTotalRows() {
		log.info("pb_service_getTotalRows");
		return pbmapper.getTotalRows();
	}
	
	public int getSearchTotalRows(String searchKey,String searchWord) {
		log.info("pb_service_getSearchTotalRows");
		if(searchKey.equals("party_board_id")) {
			return pbmapper.getSearchTotalRowsBoardId(searchWord);
		}else if(searchKey.equals("party_board_writer")) {
			return pbmapper.getSearchTotalRowsBoardWriter("%"+searchWord+"%");
		}else if(searchKey.equals("boss")) {
			return pbmapper.getSearchTotalRowsBoss("%"+searchWord+"%");
		}else {
			return pbmapper.getSearchTotalRowsBoardWdate("%"+searchWord+"%"); 
		}
		
	}
}
