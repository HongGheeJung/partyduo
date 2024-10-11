package partyDuo.com.model;

import lombok.Data;

@Data
public class PartyVO {
	private int party_id;
	private String party_name;
	private int party_master;//member_id
}
