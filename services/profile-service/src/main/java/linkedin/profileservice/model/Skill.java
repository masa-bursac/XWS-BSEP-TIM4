package linkedin.profileservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


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

	public Skill() {
		super();
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
}
