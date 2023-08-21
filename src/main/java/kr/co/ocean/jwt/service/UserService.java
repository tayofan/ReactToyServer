package kr.co.ocean.jwt.service;

import kr.co.ocean.jwt.dto.TokenDto;

public interface UserService {

	public TokenDto loginCheck(String id, String pswd) throws Exception;
	
	public String RefreshAccessToken(String refreshToken) throws Exception;
}
