package partyDuo.com.model;



import java.sql.Timestamp;

import lombok.Data;

@Data
public class NoticeVO {
	private int notice_id;
	private String notice_title;
	private String notice_content;
	private String notice_writer;
	private Timestamp notice_wdate;
	
}
