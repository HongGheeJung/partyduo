package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.ReportBoardVO;

@Mapper
public interface ReportBoardMapper {
    public int insertOK(ReportBoardVO vo);
    
    public int updateOK(ReportBoardVO vo);
    
    public int deleteOK(ReportBoardVO vo);

    public ReportBoardVO selectOne(ReportBoardVO vo);
    
    public List<ReportBoardVO> selectAllPageBlock(int startRow, int pageBlock);
    
    public List<ReportBoardVO> selectAll();

    public List<ReportBoardVO> searchListTitlePageBlock(String searchWord, int startRow, int pageBlock);
    
    public List<ReportBoardVO> searchListWriterPageBlock(String searchWord, int startRow, int pageBlock);
    
    public List<ReportBoardVO> searchListWdatePageBlock(String searchWord, int startRow, int pageBlock);
    
    public int getTotalRows();
    
    public int getSearchTotalRowsTitle(String searchWord);
    
    public int getSearchTotalRowsWriter(String searchWord);
    
    public int getSearchTotalRowsWdate(String searchWord);
}
