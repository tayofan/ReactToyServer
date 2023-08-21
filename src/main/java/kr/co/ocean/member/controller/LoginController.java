package kr.co.ocean.member.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ocean.member.dto.MemberVO;
import kr.co.ocean.member.service.MemberService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private MemberService memberService;
	
	public LoginController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/check")
	public String login(HttpServletRequest request, @RequestBody Map param) throws Exception {
		System.out.println(param);
		HttpSession session = request.getSession();
		
		MemberVO member = memberService.checkLogin(param);
		
		String message = "아이디 혹은 비밀번호가 일치하지 않습니다.";
		
		if(member.getActive().equals("false")) {
			message = "휴면계정";
		}else {
			session.setAttribute("loginMember", member);
			message = "로그인";
		}
		return message;
	}
	
}
