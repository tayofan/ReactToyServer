package kr.co.ocean;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.ocean.jwt.dao.UserMapper;
import kr.co.ocean.jwt.dto.UserImpl;
import kr.co.ocean.jwt.service.UserService;
import kr.co.ocean.kafka.service.AdminService;
import kr.co.ocean.member.service.MemberService;

@SpringBootTest
class ReactToyServerApplicationTests {
	
//	private MemberService memberService;
//	private UserMapper userMapper;
//	private final UserService userService;
	private final AdminService adminService;
	
	@Autowired
	public ReactToyServerApplicationTests(AdminService adminService){
		this.adminService = adminService;
	}
	
	@Test
	void contextLoads() throws Exception {
		
//		System.out.println(userService.loginCheck(id, pswd));
		adminService.getTopicInfos();
		
	}

}
