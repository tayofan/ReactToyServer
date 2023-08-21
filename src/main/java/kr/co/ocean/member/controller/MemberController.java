package kr.co.ocean.member.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ocean.jwt.dto.TokenDto;
import kr.co.ocean.member.dto.MemberVO;
import kr.co.ocean.member.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	private Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/login")
	public String login(HttpServletRequest request, @RequestBody Map param) throws Exception {
		
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
	
	@PostMapping("/getMemberInfo")
	public ResponseEntity<MemberVO> getMember(@RequestBody TokenDto tokenDto, HttpServletRequest request) throws Exception {
		ResponseEntity<MemberVO> entity = null;
		String accessToken = resolveToken(request);
		
		try {
			List<MemberVO> members = memberService.getMemberInfoById(accessToken);
			entity = new ResponseEntity<MemberVO>(members.get(0), HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<MemberVO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return entity;
	}
	
	private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
	
	
}
