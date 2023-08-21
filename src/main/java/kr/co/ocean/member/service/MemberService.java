package kr.co.ocean.member.service;

import java.util.List;
import java.util.Map;

import kr.co.ocean.member.dto.MemberVO;

public interface MemberService {
	
	/**
	 * 로그인 체크
	 * @param param
	 * @return
	 * @throws Exception
	 */
	MemberVO checkLogin(Map param) throws Exception;
	
	List<MemberVO> getMemberInfoById(String AccessToken) throws Exception;
	
}
