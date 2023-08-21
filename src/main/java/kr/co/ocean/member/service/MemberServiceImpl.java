package kr.co.ocean.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.ocean.jwt.TokenProvider;
import kr.co.ocean.member.dao.MemberMapper;
import kr.co.ocean.member.dto.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper memberMapper;
	private final TokenProvider tokenProvider;
	
	public MemberServiceImpl(MemberMapper memberMapper, TokenProvider tokenProvider) {
		this.memberMapper = memberMapper;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public MemberVO checkLogin(Map param) throws Exception {
		return memberMapper.selectisLogin(param).get(0);
	}

	@Override
	public List<MemberVO> getMemberInfoById(String AccessToken) throws Exception {
				
		String id = tokenProvider.getUserName(AccessToken);
		
		return memberMapper.selectMemberInfoById(id);
	}
	
}
