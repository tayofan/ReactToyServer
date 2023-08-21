package kr.co.ocean.jwt.dto;

public class TokenDto {
	private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
    private String refreshToken;
    
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Long getTokenExpiresIn() {
		return tokenExpiresIn;
	}
	public void setTokenExpiresIn(Long tokenExpiresIn) {
		this.tokenExpiresIn = tokenExpiresIn;
	}
}
