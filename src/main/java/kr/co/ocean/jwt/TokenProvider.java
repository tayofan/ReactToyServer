package kr.co.ocean.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.ocean.jwt.dto.TokenDto;

@Component
public class TokenProvider {
	
	private Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	
	private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

	private final Key key;

	public TokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String createAccessToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();

		// Access Token 생성
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder().setSubject(authentication.getName()) // payload "sub": "name"
				.setId(authorities)
				.claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
				.setExpiration(accessTokenExpiresIn) // payload "exp": 1516239022 (예시)
				.signWith(key, SignatureAlgorithm.HS512) // header "alg": "HS512"
				.compact();
		
		return accessToken;
	}
	
	public String createRefreshToken(String username) {
		long now = (new Date()).getTime();
		
		String refreshToken = Jwts.builder().setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
				.setSubject(username)
				.signWith(key, SignatureAlgorithm.HS512).compact();
		
		return refreshToken;
	}
	
	public TokenDto generateTokenDto(Authentication authentication) {
		// 권한들 가져오기
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();

		// Access Token 생성
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder().setSubject(authentication.getName()) // payload "sub": "name"
				.claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
				.setExpiration(accessTokenExpiresIn) // payload "exp": 1516239022 (예시)
				.signWith(key, SignatureAlgorithm.HS512) // header "alg": "HS512"
				.compact();

		// Refresh Token 생성
		String refreshToken = Jwts.builder().setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
				.setSubject(authentication.getName())
				.signWith(key, SignatureAlgorithm.HS512).compact();
		
		TokenDto tokenDto = new TokenDto();
		tokenDto.setAccessToken(accessToken);
		tokenDto.setRefreshToken(refreshToken);
		tokenDto.setGrantType(BEARER_TYPE);
		tokenDto.setTokenExpiresIn(accessTokenExpiresIn.getTime());
		
		return tokenDto;
	}
	
	public String getUserName(String refreshToken) {
		Claims claims = parseClaims(refreshToken);
		String userName = claims.getSubject();
		
		if (userName == null || userName.equals("")) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		return userName;
	}
	
	public Authentication getAuthentication(String accessToken) {
		// 토큰 복호화
		Claims claims = parseClaims(accessToken);
		if (claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		// 클레임에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		// UserDetails 객체를 만들어서 Authentication 리턴
		UserDetails principal = new User(claims.getSubject(), "", authorities);
//				User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			logger.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			logger.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			logger.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

}
