package partyDuo.com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.service.FavoriteService;

@Slf4j
@Controller
public class FavoriteController {
	
	@Autowired
	FavoriteService service;
	
}
