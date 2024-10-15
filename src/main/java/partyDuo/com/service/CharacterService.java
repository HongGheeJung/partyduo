package partyDuo.com.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CharacterService {
	public String character_info() {
		log.info("character_info()...");
		return "i";
	}
}
