package linkedin.postservice.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "postInfo")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostInfo {
	
	@Transient
    public static final String SEQUENCE_NAME = "postInfo_sequence";
	
	 @Id
	 private int id;
	 
	 @Field
	 private LocalDate date;
	 
	 @Field
	 private String caption;
	 
	 @Field
	 private List<Integer> picture;
}
