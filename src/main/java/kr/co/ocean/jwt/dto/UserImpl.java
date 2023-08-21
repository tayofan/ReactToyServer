package kr.co.ocean.jwt.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserImpl implements UserDetails {

	private String id;
	private String pswd;
	private List<String> roles;

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pswd;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(String roleList) {
		this.roles = Arrays.stream(roleList.split(",")).collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "UserImpl [id=" + id + ", pswd=" + pswd + ", roles=" + roles + "]";
	}
}
