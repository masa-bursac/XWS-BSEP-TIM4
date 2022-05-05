package linkedin.profileservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import linkedin.profileservice.dto.SkillDTO;


@Document(collection = "skill")
public class Skill {
	
	@Transient
    public static final String SEQUENCE_NAME = "skill_sequence";
	
	@Id
	private int id;
	
	@Field
	private String name;
	
	@Field
	private String otherInfo;
	
	@Field
	private int userInfoId;

	public Skill() {
		super();
	}
	
	public Skill(SkillDTO skillDTO) {
		this.name= skillDTO.getName();
		this.otherInfo = skillDTO.getOtherInfo();
		this.userInfoId = skillDTO.getUserInfoId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public int getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(int userInfoId) {
		this.userInfoId = userInfoId;
	}
}
