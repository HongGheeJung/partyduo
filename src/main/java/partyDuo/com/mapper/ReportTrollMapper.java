package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import partyDuo.com.model.ReportTrollVO;



@Mapper
public interface ReportTrollMapper {
	public int insertOK(ReportTrollVO vo);
	
	public int updateOK(ReportTrollVO vo);
	
	public int deleteOK(ReportTrollVO vo);

	public ReportTrollVO selectOne(ReportTrollVO vo);
	
	public List<ReportTrollVO> selectAllPageBlock(int startRow, int pageBlock);
	public List<ReportTrollVO> selectAll();

	public List<ReportTrollVO> searchListReportTrollCharacterPageBlock(String searchWord, int startRow, int pageBlock);
	public List<ReportTrollVO> searchListReportTrollWdatePageBlock(String searchWord, int startRow, int pageBlock);
	
	public int getTotalRows();
	public int getSearchTotalRowsReportTrollCharacter(String searchWord);
	public int getSearchTotalRowsReportTrollWdate(String searchWord);
}
