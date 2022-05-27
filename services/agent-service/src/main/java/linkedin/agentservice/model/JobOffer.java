package linkedin.agentservice.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "jobOffer")
public class JobOffer {
	
	@Transient
    public static final String SEQUENCE_NAME = "job_sequence";
	
	@Id
    private int id;
	
	@Field
    private String jobPosition;
	
    @Field
    private List<Comment> comments;
    
    @Field
    private String salary;
    
    @Field
    private List<Integer> marks;
    
    @Field
    private String selection;
    
    

	public JobOffer() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public List<Integer> getMarks() {
		return marks;
	}

	public void setMarks(List<Integer> marks) {
		this.marks = marks;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}
    
    
}
