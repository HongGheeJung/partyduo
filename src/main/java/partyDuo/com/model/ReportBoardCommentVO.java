package partyDuo.com.model;


import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class ReportBoardCommentVO {
	private int report_board_comment_id;
	private int report_board_id;
	private String report_board_comment_content;
	private String report_board_comment_writer;
	private Timestamp report_board_comment_wdate;
	
}
