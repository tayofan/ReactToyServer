package kr.co.ocean.member.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MemberVO {
	private String id;
	private String name;
	private String email;
	private String pswd;
	private String active;
	private String rolestxt;
	private String lastLoginDate;
	private String regDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getRolestxt() {
		return rolestxt;
	}
	public void setRolestxt(String rolestxt) {
		this.rolestxt = roles.toString();
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public String getUpdtDate() {
		return updtDate;
	}
	public void setUpdtDate(String updtDate) {
		this.updtDate = updtDate;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	private String delDate;
	private String updtDate;
	
	private List<String> roles;

	
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(String roleList) {
		this.roles = Arrays.stream(roleList.split(",")).collect(Collectors.toList());
	}
	
	
	
}
