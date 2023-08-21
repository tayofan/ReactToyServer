package kr.co.ocean.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.ocean.jwt.dto.TokenDto;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	private final TokenProvider tokenProvider;
	
	public JwtAuthenticationFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String accessToken = resolveToken((HttpServletRequest) request);
		String refreshToken = resolveRefreshToken(request);
		ObjectMapper objectMapper = new ObjectMapper();
		
        // 2. validateToken 으로 토큰 유효성 검사
        if (accessToken != null) {
        	if(tokenProvider.validateToken(accessToken)) {
        		// 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
        		Authentication authentication = tokenProvider.getAuthentication(accessToken);
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        		logger.info("인증성공");
        	} else {
        		if(tokenProvider.validateToken(refreshToken)) {
        			logger.info("재발급");
            		response.setStatus(400);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    Authentication authentication = tokenProvider.getAuthentication(accessToken);
                    TokenDto dto = tokenProvider.generateTokenDto(authentication); 
                                        
            		response.getWriter().write(objectMapper.writeValueAsString(dto));
            		response.setHeader("Authorization", dto.getAccessToken());
            		response.setHeader("RefreshToken", dto.getRefreshToken());
            		return;
        		} else {
        			logger.info("재로그인 필요");
            		response.setStatus(444);
        		}
        	}
        }
        filterChain.doFilter(request, response);
		
	}
	
	// Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
 // Request Header 에서 RefreshToken토큰 정보 추출
    private String resolveRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("RefreshToken");
        
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer")) {
            return refreshToken.substring(7);
        }
        return null;
    }
}
