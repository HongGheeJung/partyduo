package partyDuo.com.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.spiralmoon.maplestory.api.MapleStoryApi;
import dev.spiralmoon.maplestory.api.MapleStoryApiException;
import dev.spiralmoon.maplestory.api.dto.character.CharacterListAccountDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterListDTO;
import dev.spiralmoon.maplestory.api.dto.ranking.UnionRankingResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CharacterService {
	private MapleStoryApi api;
	public String character_info() {
		log.info("character_info()...");
		return "i";
	}
	public String foundOcid(String apikey) {
		apikey = "test_0046c440e850ceb282a6138bb606bc06a377b0d377369f674d9aa9a74d43175befe8d04e6d233bd35cf2fabdeb93fb0d";
		api= new MapleStoryApi(apikey);
		CharacterListDTO response=new CharacterListDTO(null);
		
		try {
			response=api.getCharacterList();
			
		}
		catch(Exception e) {
			if(e instanceof MapleStoryApiException) {
				log.info("error or not found");
				return null;
			}else {
				log.info("unknown");
				return null;
			}
		}
		return response.getAccountList().get(0).getCharacterList().get(0).getOcid();
	}
	public String bonCharacter(String apikey) {
		UnionRankingResponseDTO response;
		String ocid=foundOcid(apikey);
		try {
			response = api.getUnionRanking(null, ocid, null);
		} catch (Exception e) {
			log.info("error or not found");
			return null;
		}
		return response.getRanking().get(0).getCharacterName();
	}
}
