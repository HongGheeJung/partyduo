package partyDuo.com.model;



import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReportBoardVO {
	private int report_board_id;
	private String report_board_title;
	private String report_board_content;
	private String report_board_writer;
	private Timestamp report_board_wdate;
	
}
