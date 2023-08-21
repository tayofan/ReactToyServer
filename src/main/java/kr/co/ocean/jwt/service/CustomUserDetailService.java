package kr.co.ocean.jwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ocean.jwt.dao.UserMapper;
import kr.co.ocean.jwt.dto.UserImpl;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	
	public CustomUserDetailService(PasswordEncoder passwordEncoder, UserMapper userMapper) {
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserImpl userImpl;
		userImpl = userMapper.selectUserInfo(username);
		
		if(userImpl == null) {
			throw new UsernameNotFoundException("해당하는 유저가 없습니다."); 
		}

		return User.builder()
				.username(userImpl.getUsername())
				.password(passwordEncoder.encode(userImpl.getPassword()))
				.roles(userImpl.getRoles().toArray(new String[0]))
				.build();

	}
}
