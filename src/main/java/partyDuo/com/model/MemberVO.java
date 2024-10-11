package partyDuo.com.model;

import java.sql.Date;

import lombok.Data;

@Data
public class MemberVO {
	private int member_id;
	private String id;
	private String pw;
	private String email;
	private Date regdate;
	private String apikey;
	private String character_name;
}
