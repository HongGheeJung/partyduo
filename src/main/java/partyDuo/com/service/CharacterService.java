package partyDuo.com.service;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;

import dev.spiralmoon.maplestory.api.MapleStoryApi;
import dev.spiralmoon.maplestory.api.MapleStoryApiException;
import dev.spiralmoon.maplestory.api.dto.character.*;
import dev.spiralmoon.maplestory.api.dto.ranking.UnionRankingResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CharacterService {
	private MapleStoryApi api=new MapleStoryApi("test_0046c440e850ceb282a6138bb606bc06a377b0d377369f674d9aa9a74d43175befe8d04e6d233bd35cf2fabdeb93fb0d");
//	private MapleStoryApi api=new MapleStoryApi("live_0046c440e850ceb282a6138bb606bc068953c11b80af7d903b911c271d5772c7efe8d04e6d233bd35cf2fabdeb93fb0d");
	//닉네임으로 ocid 찾기
	public String foundOcidByName(String character_name) {
		CharacterDTO response;
		try {
			response = api.getCharacter(character_name);
		} catch (Exception e) {
			log.info("error or not found");
			return null;
		}
		
		return response.getOcid();
	}
	//apikey로 ocid 찾기
	public String foundOcid(String apikey) {
//		log.info("foundOcid");
		api= new MapleStoryApi(apikey);
		CharacterListDTO response;
		
		try {
			response=api.getCharacterList();
//			log.info("response:{}", response);
//			log.info("result:{}",response.getAccountList().get(0).getCharacterList().get(0).getOcid());
			
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
	//foundOcid로 찾은 ocid로 본캐 찾기
	public String bonCharacter(String ocid) {
		UnionRankingResponseDTO response;
//		log.info("본캐찾기");
//		log.info("ocid:{}", ocid);
		try {
			response = api.getUnionRanking(null, ocid, null);
		} catch (Exception e) {
			log.info("error or not found");
			return null;
		}
		return response.getRanking().get(0).getCharacterName();
	}
	//캐릭터 기본 정보 조회
	public CharacterBasicDTO character_basic(String ocid) {
		CharacterBasicDTO response;
		try {
			response= api.getCharacterBasic(ocid);
		} catch (Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//캐릭터 기본 스탯 조회
	public CharacterStatDTO character_stat(String ocid) {
		CharacterStatDTO response;
		try {
			response= api.getCharacterStat(ocid);
		} catch (Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//캐릭터 하이퍼스탯 조회
	public CharacterHyperStatDTO character_hyperstat(String ocid) {
		CharacterHyperStatDTO response;
		try {
			response=api.getCharacterHyperStat(ocid);
		} catch (Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//캐릭터 성향 조회
	public CharacterPropensityDTO character_propensity(String ocid) {
		CharacterPropensityDTO response;
		try {
			response=api.getCharacterPropensity(ocid);
		} catch (Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//캐릭터 어빌리티 조회
	public CharacterAbilityDTO character_ability(String ocid) {
		CharacterAbilityDTO response;
		try {
			response=api.getCharacterAbility(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//캐릭터 아이템 조회
	public CharacterItemEquipmentDTO character_itemEquipment(String ocid) {
		CharacterItemEquipmentDTO response;
		try {
			response=api.getCharacterItemEquipment(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//캐릭터 캐시 아이템 조회
	public CharacterCashItemEquipmentDTO character_cashItemEquipment(String ocid) {
		CharacterCashItemEquipmentDTO response;
		try {
			response=api.getCharacterCashItemEquipment(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//캐릭터 심볼 조회
	public CharacterSymbolEquipmentDTO character_symbolEquipment(String ocid) {
		CharacterSymbolEquipmentDTO response;
		try {
			response=api.getCharacterSymbolEquipment(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//장착 세트 효과 조회
	public CharacterSetEffectDTO character_setEffect(String ocid) {
		CharacterSetEffectDTO response;
		try {
			response=api.getCharacterSetEffect(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//헤어, 성형, 피부 등 외형 조회
	public CharacterBeautyEquipmentDTO character_beautyEquipment(String ocid) {
		CharacterBeautyEquipmentDTO response;
		try {
			response=api.getCharacterBeautyEquipment(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//안드로이드 정보 조회
	public CharacterAndroidEquipmentDTO character_androidEquipment(String ocid) {
		CharacterAndroidEquipmentDTO response;
		try {
			response=api.getCharacterAndroidEquipment(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//펫 정보 조회
	public CharacterPetEquipmentDTO character_petEquipment(String ocid) {
		CharacterPetEquipmentDTO response;
		try {
			response=api.getCharacterPetEquipment(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//스킬 정보 조회
	public CharacterSkillDTO character_skill(String ocid, String skill_lv) {
		CharacterSkillDTO response;
		try {
			response=api.getCharacterSkill(ocid, skill_lv);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//링크 정보 조회
	public CharacterLinkSkillDTO character_linkSkill(String ocid) {
		CharacterLinkSkillDTO response;
		try {
			response=api.getCharacterLinkSkill(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//VMatrix(5차 강화) 
	public CharacterVMatrixDTO character_vmatrix(String ocid) {
		CharacterVMatrixDTO response;
		try {
			response=api.getCharacterVMatrix(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//HexaMatrix(6차 강화) 
	public CharacterHexaMatrixDTO character_hexamatrix(String ocid) {
		CharacterHexaMatrixDTO response;
		try {
			response=api.getCharacterHexaMatrix(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//HexaMatrixStat(6차 스탯 강화) 
	public CharacterHexaMatrixStatDTO character_hexamatrixstat(String ocid) {
		CharacterHexaMatrixStatDTO response;
		try {
			response=api.getCharacterHexaMatrixStat(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	//Dojang(도장 점수) 
	public CharacterDojangDTO character_dojang(String ocid) {
		CharacterDojangDTO response;
		try {
			response=api.getCharacterDojang(ocid);
		}catch(Exception e) {
			log.info("error or not found");
			return null;
		}
		return response;
	}
	
}
