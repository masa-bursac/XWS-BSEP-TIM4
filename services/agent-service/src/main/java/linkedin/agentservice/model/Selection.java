package linkedin.agentservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "selection")
public class Selection {
	
	@Transient
    public static final String SEQUENCE_NAME = "selection_sequence";
	
	@Id
    private int id;
	
	@Field
    private String comment;
	
	@Field
    private String duration;
	
	@Field
    private int mark;

	public Selection() {
		super();
	}
	
	

	public Selection(int id, String comment, String duration, int mark) {
		super();
		this.id = id;
		this.comment = comment;
		this.duration = duration;
		this.mark = mark;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	
	

}
