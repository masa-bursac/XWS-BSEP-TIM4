package linkedin.pictureservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "link")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Link {
	
	@Transient
    public static final String SEQUENCE_NAME = "link_sequence";
	
	 @Id
	 private int id;
	 
	 @Field
	 private byte[] path;
}
