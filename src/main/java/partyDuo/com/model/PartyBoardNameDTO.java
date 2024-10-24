package partyDuo.com.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import partyDuo.com.model.PartyBoardVO;

@Data
@AllArgsConstructor
public class PartyBoardNameDTO {
	private PartyBoardVO vo;
	private String party_name;
	private String party_world;
	private String bossImagePath;
}
