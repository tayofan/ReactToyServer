package kr.co.ocean.jwt.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import kr.co.ocean.jwt.TokenProvider;
import kr.co.ocean.jwt.dto.TokenDto;
import kr.co.ocean.jwt.service.UserService;

@RestController
@RequestMapping("/api/login2")
public class JwtController {
	
	private Logger logger = LoggerFactory.getLogger(JwtController.class);
	
	private final UserService userService;
	private final TokenProvider tokenProvider;
	
	public JwtController(UserService userService, TokenProvider tokenProvider) {
		this.userService = userService;
		this.tokenProvider = tokenProvider;
	}
	
	@PostMapping("/check")
	public ResponseEntity<TokenDto> login(@RequestBody Map param) throws Exception {
		ResponseEntity<TokenDto> entity = null;
		String id = (String) param.get("id");
		String pswd = (String) param.get("pswd");
		
		try {
			TokenDto tokenDto = userService.loginCheck(id, pswd);
			entity = new ResponseEntity<>(tokenDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
		return entity;
	}
	
	@PostMapping("/checkAccess")
	public ResponseEntity<String> checkAccess(@RequestBody TokenDto tokenDto) throws Exception {
		
		ResponseEntity<String> entity = null;
		String refreshToken = tokenDto.getRefreshToken();
		String accessToken = tokenDto.getAccessToken();
		
		try {
			tokenProvider.validateToken(accessToken);
			entity = new ResponseEntity<>("success", HttpStatus.OK);
		} catch (ExpiredJwtException e) {
			entity = new ResponseEntity<>("401", HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			entity = new ResponseEntity<>("401", HttpStatus.EXPECTATION_FAILED);
		}
		
		return entity;		
	}
	
	@PostMapping("/test")
	public String test() {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		System.out.println(context.toString());
		return "success";
	}
	
}
