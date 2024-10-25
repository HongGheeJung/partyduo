package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.ReportTrollVO;



@Mapper
public interface ReportBoardMapper {
	public int insertOK(ReportTrollVO vo);
	
	public int updateOK(ReportTrollVO vo);
	
	public int deleteOK(ReportTrollVO vo);

	public ReportTrollVO selectOne(ReportTrollVO vo);
	
	public List<ReportTrollVO> selectAllPageBlock(int startRow, int pageBlock);
	public List<ReportTrollVO> selectAll();

	
	public List<ReportTrollVO> searchListReportBoardTitlePageBlock(String searchWord, int startRow, int pageBlock);
	public List<ReportTrollVO> searchListReportBoardWriterPageBlock(String searchWord, int startRow, int pageBlock);
	public List<ReportTrollVO> searchListReportBoardWdatePageBlock(String searchWord, int startRow, int pageBlock);
	
	public int getTotalRows();
	public int getSearchTotalRowsReportBoardTitle(String searchWord);
	public int getSearchTotalRowsReportBoardWriter(String searchWord);
	public int getSearchTotalRowsReportBoardWdate(String searchWord);
	

	
	
	

}
