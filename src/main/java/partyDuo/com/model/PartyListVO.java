package partyDuo.com.model;

import lombok.Data;

@Data
public class PartyListVO {
	private int party_id;
	private int member_id;
	private Boolean party_join;
}
