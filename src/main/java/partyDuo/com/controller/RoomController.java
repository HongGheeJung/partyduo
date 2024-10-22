package partyDuo.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import partyDuo.com.model.MemberVO;
import partyDuo.com.model.PartyListVO;
import partyDuo.com.model.PartyVO;
import partyDuo.com.service.MemberService;
import partyDuo.com.service.PartyListService;
import partyDuo.com.service.PartyService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class RoomController {
	

	@Autowired
	PartyService pservice;
	
	@GetMapping("/rooms")
    public String rooms(Model model){
		log.info("/rooms");
		String roomId= "1";
        model.addAttribute("list", pservice.searchList("party_id", roomId));

        return "rooms";
    }
	
	@GetMapping("/room")
    public void getRoom(String roomId, Model model){

        log.info("# get Chat Room, roomID : " + roomId);

        model.addAttribute("room", pservice.searchList("party_id", roomId));
    }
	
	
}
