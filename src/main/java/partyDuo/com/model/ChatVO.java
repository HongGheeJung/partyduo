package partyDuo.com.model;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class ChatVO {
	private int chat_id;
	private int party_id;
	private Timestamp chat_wdate;
	private String chat_writer;
	private String chat_content;

}
