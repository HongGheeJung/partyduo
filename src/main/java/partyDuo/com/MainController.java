package partyDuo.com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	@GetMapping({"/","/main"})//웰컴파일없이 제어가능(index.html무시)
	public String home() {
		log.info("/main");
		return "main";//resources/templates폴더에서 찾는다
	}
}
