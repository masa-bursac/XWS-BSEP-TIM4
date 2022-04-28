package linkedin.profileservice.dto;


public class ProfileDTO {
		
		private int userInfoId; 
		
	 	private String username;
	    
	    private String name;
	    
	    private String surname;

		public ProfileDTO(int id,String username, String name, String surname) {
			// TODO Auto-generated constructor stub
			this.userInfoId = id;
			this.username = username;
			this.name = name;
			this.surname = surname;
		}

		public int getId() {
			return userInfoId;
		}

		public void setId(int id) {
			this.userInfoId = id;
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

}
