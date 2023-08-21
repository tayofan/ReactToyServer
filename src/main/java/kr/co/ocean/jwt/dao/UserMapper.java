package kr.co.ocean.jwt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ocean.jwt.dto.RefreshTokenDto;
import kr.co.ocean.jwt.dto.UserImpl;

@Mapper
public interface UserMapper {
	
	UserImpl selectUserInfo(String username);
	
	RefreshTokenDto selectRefreshToken(String refreshToken) throws Exception;
	
}
