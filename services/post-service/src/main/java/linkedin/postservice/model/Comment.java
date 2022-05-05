package linkedin.postservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
	
	@Transient
    public static final String SEQUENCE_NAME = "comment_sequence";
	
	 @Id
	 private int id;
	 
	 @Field
	 private int profileId;	
	 
	 @Field
	 private String content;
	 
	 public Comment(int profileId, String content)
	    {
	        this.profileId = profileId;
	        this.content = content;
	    }
}
