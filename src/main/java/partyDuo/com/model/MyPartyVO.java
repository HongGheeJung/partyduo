package partyDuo.com.model;

import lombok.Data;

@Data
public class MyPartyVO {
	private int party_id;
	private String party_name;
	private String party_master;
	private int member_id;
	private Boolean party_join;
}
