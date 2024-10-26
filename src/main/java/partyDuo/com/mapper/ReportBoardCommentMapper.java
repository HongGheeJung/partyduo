package partyDuo.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import partyDuo.com.model.ReportBoardCommentVO; // 수정된 VO

@Mapper
public interface ReportBoardCommentMapper {
    public int insertOK(ReportBoardCommentVO vo); // 신고 댓글 추가
    
    public int updateOK(ReportBoardCommentVO vo); // 신고 댓글 수정
    
    public int deleteOK(ReportBoardCommentVO vo); // 신고 댓글 삭제
    
    public ReportBoardCommentVO selectOne(ReportBoardCommentVO vo); // 신고 댓글 조회

    public List<ReportBoardCommentVO> searchListReportBoardId(String searchWord); // 신고 댓글 검색
}
