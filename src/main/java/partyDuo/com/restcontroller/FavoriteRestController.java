package partyDuo.com.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.service.FavoriteService;

@Slf4j
@RestController
public class FavoriteRestController {
	
	@Autowired
	FavoriteService service;
	
	
	
	
	
}
