package partyDuo.com.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.FavoriteVO;
import partyDuo.com.model.MemberVO;
import partyDuo.com.service.FavoriteService;
import partyDuo.com.service.MemberService;

@Slf4j
@Controller
public class FavoriteController {
	
	@Autowired
	FavoriteService fservice;
	
	@Autowired
	MemberService mservice;
	
	@Autowired
	HttpSession session;
}