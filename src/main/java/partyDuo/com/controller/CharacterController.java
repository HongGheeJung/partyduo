package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.spiralmoon.maplestory.api.dto.character.CharacterAbilityDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterAndroidEquipmentDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterBasicDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterBeautyEquipmentDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterCashItemEquipmentDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterHyperStatDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterItemEquipmentDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterLinkSkillDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterPetEquipmentDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterPropensityDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterSetEffectDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterSkillDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterStatDTO;
import dev.spiralmoon.maplestory.api.dto.character.CharacterSymbolEquipmentDTO;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.CharacterService;

@Slf4j
@Controller
public class CharacterController {
	@Autowired
	CharacterService service;
	
	@GetMapping("/character/info")
	public String character_info(MemberVO vo) {
		CharacterStatDTO stat=service.character_stat(vo.getOcid());
		return "character/info";
	}
	
	//api값 호출 테스트용 responsebody
	@ResponseBody
	@GetMapping("/character/infoTestBasic")
	public CharacterBasicDTO character_basic() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterBasicDTO stat=service.character_basic(ocid);
		return stat;
	}
	
	@ResponseBody
	@GetMapping("/character/infoTestStat")
	public CharacterStatDTO character_stat() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterStatDTO stat=service.character_stat(ocid);
		return stat;
	}
	@ResponseBody
	@GetMapping("/character/infoTestHyperStat")
	public CharacterHyperStatDTO character_hyperstat() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterHyperStatDTO hyperstat=service.character_hyperstat(ocid);
		return hyperstat;
	}
	@ResponseBody
	@GetMapping("/character/infoTestPropensity")
	public CharacterPropensityDTO character_propensity() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterPropensityDTO result=service.character_propensity(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestAbility")
	public CharacterAbilityDTO character_ability() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterAbilityDTO result=service.character_ability(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestItemEquipment")
	public CharacterItemEquipmentDTO character_itemEquipment() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterItemEquipmentDTO result=service.character_itemEquipment(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestCashItemEquipment")
	public CharacterCashItemEquipmentDTO character_cashItemEquipment() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterCashItemEquipmentDTO result=service.character_cashItemEquipment(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestSymbolEquipment")
	public CharacterSymbolEquipmentDTO character_symbolEquipment() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterSymbolEquipmentDTO result=service.character_symbolEquipment(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestSetEffect")
	public CharacterSetEffectDTO character_sefEffect() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterSetEffectDTO result=service.character_setEffect(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestBeautyEquipment")
	public CharacterBeautyEquipmentDTO character_beautyEquipment() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterBeautyEquipmentDTO result=service.character_beautyEquipment(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestAndroidEquipment")
	public CharacterAndroidEquipmentDTO character_androidEquipment() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterAndroidEquipmentDTO result=service.character_androidEquipment(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestPetEquipment")
	public CharacterPetEquipmentDTO character_petEquipment() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterPetEquipmentDTO result=service.character_petEquipment(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestSkill")
	public CharacterSkillDTO character_skill() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		String skill_lv="4";
		CharacterSkillDTO result=service.character_skill(ocid, skill_lv);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestLinkSkill")
	public CharacterLinkSkillDTO character_linkSkill() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		String skill_lv="4";
		CharacterLinkSkillDTO result=service.character_linkSkill(ocid);
		return result;
	}
}
