package linkedin.profileservice.dto;

import linkedin.profileservice.model.UserInfo;

public class UserAccessDTO {

	private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String token;
    private int tokenExpiresIn;
    
	public UserAccessDTO(UserInfo user) {
		// TODO Auto-generated constructor stub
		this.username = user.getUsername();
		this.id = user.getId();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getTokenExpiresIn() {
		return tokenExpiresIn;
	}
	public void setTokenExpiresIn(int tokenExpiresIn) {
		this.tokenExpiresIn = tokenExpiresIn;
	}
}
