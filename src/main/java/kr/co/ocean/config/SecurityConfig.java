package kr.co.ocean.config;

import java.util.List;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties.CoreConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.co.ocean.jwt.JwtAuthenticationFilter;
import kr.co.ocean.jwt.TokenProvider;
import kr.co.ocean.jwt.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final TokenProvider tokenProvider;
	
	public SecurityConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(t -> t.disable());
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(t -> t.disable());
        http.cors(t -> {
        	CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOriginPatterns(List.of("*"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            config.setAllowedHeaders(List.of("*"));
            config.setExposedHeaders(List.of("*"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
        	
        	
        	t.configurationSource(source);
        });
        http.authorizeHttpRequests(t -> {
        	t.requestMatchers("/api/login2/**").permitAll();
        	t.requestMatchers("/kafka/test/**").permitAll();
//    		t.requestMatchers("/api/board/**").hasRole("USER");
    		t.requestMatchers("/api/board/**").permitAll();
    		t.anyRequest().authenticated();
        });
//        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
                
                
//                .requestMatchers("/members/login").permitAll()
//                .requestMatchers("/api/login2/check").permitAll()
//                .requestMatchers("/api/login2/test").hasRole("USER")
//                .requestMatchers("/members/test").hasRole("USER")
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
}
