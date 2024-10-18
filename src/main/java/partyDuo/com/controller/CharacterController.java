package partyDuo.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.spiralmoon.maplestory.api.dto.character.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.service.CharacterService;

@Slf4j
@Controller
public class CharacterController {
	

	@Autowired
	CharacterService service;
	
	@Autowired
	HttpSession session;
	
	
	@GetMapping("/character/info")
	public String character_info(String character_name, Model model) {
		if(character_name==null) {
			return "character/infoDefault";
		}
		log.info("character_info()...");
		//test:캐릭터 이름 아무거나 넣어도 됩니다;
//		character_name="나로티아사";
		//ocid 찾기
		String ocid=service.foundOcidByName(character_name);
		log.info("ocid: {}", ocid);
		//캐릭터 기본정보
		CharacterBasicDTO basicDTO=service.character_basic(ocid);
		model.addAttribute("basicDTO", basicDTO);
		//캐릭터 스탯 정보
		CharacterStatDTO statDTO=service.character_stat(ocid);
		model.addAttribute("statDTO", statDTO);
		log.info("{}", statDTO.getFinalStat().size());
		//캐릭터 하이퍼스탯 정보
		CharacterHyperStatDTO hyperstatDTO=service.character_hyperstat(ocid);
		model.addAttribute("hyperstatDTO", hyperstatDTO);
		hyperstatDTO.getHyperStatPreset1().get(0).getStatType();
		//캐릭터 성향 정보
		CharacterPropensityDTO propensityDTO=service.character_propensity(ocid);
		model.addAttribute("propensityDTO", propensityDTO);		
		//캐릭터 어빌리티 정보
		CharacterAbilityDTO abilityDTO=service.character_ability(ocid);
		model.addAttribute("abilityDTO", abilityDTO);		
		//캐릭터 장비 아이템 정보
		CharacterItemEquipmentDTO itemEquipDTO=service.character_itemEquipment(ocid);
		model.addAttribute("itemEquipDTO", itemEquipDTO);		
		//캐릭터 캐시 아이템 정보
		CharacterCashItemEquipmentDTO cashItemEquipDTO=service.character_cashItemEquipment(ocid);
		model.addAttribute("cashItemEquipDTO", cashItemEquipDTO);		
		//캐릭터 안드로이드 아이템 정보
		CharacterAndroidEquipmentDTO androidEquipDTO=service.character_androidEquipment(ocid);
		model.addAttribute("androidEquipDTO", androidEquipDTO);		
		//캐릭터 펫 아이템 정보
		CharacterPetEquipmentDTO petEquipDTO=service.character_petEquipment(ocid);
		model.addAttribute("petEquipDTO", petEquipDTO);		
		//캐릭터 외형 정보
		CharacterBeautyEquipmentDTO beautyEquipDTO=service.character_beautyEquipment(ocid);
		model.addAttribute("beautyEquipDTO", beautyEquipDTO);		
		//캐릭터 심볼 정보
		CharacterSymbolEquipmentDTO symbolEquipDTO=service.character_symbolEquipment(ocid);
		model.addAttribute("symbolEquipDTO", symbolEquipDTO);		
		//캐릭터 장비 아이템 세트 효과 정보
		CharacterSetEffectDTO setEffectDTO=service.character_setEffect(ocid);
		model.addAttribute("setEffectDTO", setEffectDTO);		
		//캐릭터 스킬(0차) 정보
		CharacterSkillDTO skillzeroDTO=service.character_skill(ocid, "0");
		model.addAttribute("skillzeroDTO", skillzeroDTO);		
		//캐릭터 스킬(1차) 정보
		CharacterSkillDTO skilloneDTO=service.character_skill(ocid, "1");
		model.addAttribute("skilloneDTO", skilloneDTO);		
		//캐릭터 스킬(2차) 정보
		CharacterSkillDTO skilltwoDTO=service.character_skill(ocid, "2");
		model.addAttribute("skilltwoDTO", skilltwoDTO);		
		//캐릭터 스킬(3차) 정보
		CharacterSkillDTO skillthreeDTO=service.character_skill(ocid, "3");
		model.addAttribute("skillthreeDTO", skilltwoDTO);		
		//캐릭터 스킬(4차) 정보
		CharacterSkillDTO skillfourDTO=service.character_skill(ocid, "4");
		model.addAttribute("skillfourDTO", skillfourDTO);		
		//캐릭터 스킬(4차) 정보
		CharacterSkillDTO skillhyperpassiveDTO=service.character_skill(ocid, "hyperpassive");
		model.addAttribute("skillhyperpassiveDTO", skillhyperpassiveDTO);		
		//캐릭터 스킬(4차) 정보
		CharacterSkillDTO skillhyperactiveDTO=service.character_skill(ocid, "hyperactive");
		model.addAttribute("skillhyperactiveDTO", skillhyperactiveDTO);		
		//캐릭터 스킬(5차) 정보
		CharacterSkillDTO skillfiveDTO=service.character_skill(ocid, "5");
		model.addAttribute("skillfiveDTO", skillfiveDTO);		
		//캐릭터 스킬(6차) 정보
		CharacterSkillDTO skillsixDTO=service.character_skill(ocid, "6");
		model.addAttribute("skillsixDTO", skillsixDTO);		
		//링크 스킬 정보
		CharacterLinkSkillDTO linkSkillDTO=service.character_linkSkill(ocid);
		model.addAttribute("linkSkillDTO", linkSkillDTO);		
		//V매트릭스 정보
		CharacterVMatrixDTO vmatrixDTO=service.character_vmatrix(ocid);
		model.addAttribute("vmatrixDTO", vmatrixDTO);	
		//hexa매트릭스 정보
		CharacterHexaMatrixDTO hexaMatrixDTO=service.character_hexamatrix(ocid);
		model.addAttribute("hexaMatrixDTO", hexaMatrixDTO);		
		//hexa스탯 정보
		CharacterHexaMatrixStatDTO hexaMatrixStatDTO=service.character_hexamatrixstat(ocid);
		model.addAttribute("hexaMatrixStatDTO", hexaMatrixStatDTO);		
		//무릉도장 ㅈ어보
		CharacterDojangDTO dojangDTO=service.character_dojang(ocid);
		model.addAttribute("dojangDTO", dojangDTO);		
		
		
		Object user_id=session.getAttribute("user_id");
		model.addAttribute("user_id", user_id);
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
		CharacterLinkSkillDTO result=service.character_linkSkill(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestVMatrix")
	public CharacterVMatrixDTO character_vmatrix() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterVMatrixDTO result=service.character_vmatrix(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestHexaMatrix")
	public CharacterHexaMatrixDTO character_haxamatrix() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterHexaMatrixDTO result=service.character_hexamatrix(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestHexaMatrixStat")
	public CharacterHexaMatrixStatDTO character_haxamatrixstat() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterHexaMatrixStatDTO result=service.character_hexamatrixstat(ocid);
		return result;
	}
	@ResponseBody
	@GetMapping("/character/infoTestDojang")
	public CharacterDojangDTO character_dojang() {
		String ocid="e1056a9e8886c6d29924d36ab64b0018";
		CharacterDojangDTO result=service.character_dojang(ocid);
		return result;
	}
}
