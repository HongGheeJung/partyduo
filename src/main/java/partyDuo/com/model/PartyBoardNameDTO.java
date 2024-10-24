package partyDuo.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PartyBoardNameDTO {
	private PartyBoardVO vo;
	private String party_name;
	private String party_world;
	private String bossImagePath;
}
