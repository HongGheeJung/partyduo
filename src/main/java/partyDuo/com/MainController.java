package partyDuo.com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@GetMapping({"/","/main"})
	public String home() {
		log.info("/main");
		return "main";
	}
	
	
	
	
}
