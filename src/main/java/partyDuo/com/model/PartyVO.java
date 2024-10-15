package partyDuo.com.model;

import lombok.Data;

@Data
public class PartyVO {
	private int party_id;
	private String party_name;
	private String party_master;//member_id
}
