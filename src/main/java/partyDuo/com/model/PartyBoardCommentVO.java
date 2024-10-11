package partyDuo.com.model;

import java.util.Date;

import lombok.Data;

@Data
public class PartyBoardCommentVO {
	private int party_board_comment_id;
	private int party_board_id;
	private String party_board_comment_content;
	private String party_board_comment_writer;
	private Date party_board_comment_wdate;
	
}
