package kr.co.ocean.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ocean.member.dto.MemberVO;

@Mapper
public interface MemberMapper {
	/**
	 * 게시글 추가 Mapper
	 * @param boardVO 객체 {@code BoardVO}
	 * @throws Exception
	 */
	List<MemberVO> selectisLogin(Map param) throws Exception;
	
	
	List<MemberVO> selectMemberInfoById(String id) throws Exception;
}
