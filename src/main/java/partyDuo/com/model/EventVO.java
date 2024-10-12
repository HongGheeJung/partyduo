package partyDuo.com.model;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class EventVO {
	private int event_id;
	private int party_id;
	private String event_title;
	private String event_content;
	private Timestamp event_startTime;
	private Timestamp event_endTime;
	private String event_location;
	private String event_bosstag;
	private String event_character;

}
