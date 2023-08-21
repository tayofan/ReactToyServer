package kr.co.ocean.jwt.dto;

public class RefreshTokenDto extends TokenDto{
	
	private String id;
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "RefreshTokenDto [id=" + id + "]";
	}
}
