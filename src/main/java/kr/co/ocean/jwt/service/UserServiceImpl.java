package kr.co.ocean.jwt.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import kr.co.ocean.jwt.TokenProvider;
import kr.co.ocean.jwt.dao.UserMapper;
import kr.co.ocean.jwt.dto.RefreshTokenDto;
import kr.co.ocean.jwt.dto.TokenDto;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;
	
	public UserServiceImpl(
			UserMapper userMapper,
			AuthenticationManagerBuilder authenticationManagerBuilder,
			TokenProvider tokenProvider) {
		this.userMapper = userMapper;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public TokenDto loginCheck(String id, String pswd) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, pswd);
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
		
		return tokenDto;
	}

	@Override
	public String RefreshAccessToken(String refreshToken) throws Exception {
		
		RefreshTokenDto dto = userMapper.selectRefreshToken(refreshToken);
		
		return dto.getId();
	}
	
	
}
