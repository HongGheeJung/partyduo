package partyDuo.com.model;



import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReportTrollVO {
	private int report_troll_id;
	private String character_name;
	private String report_troll_content;
	private String report_troll_writer;
	private Timestamp report_troll_wdate;
}
