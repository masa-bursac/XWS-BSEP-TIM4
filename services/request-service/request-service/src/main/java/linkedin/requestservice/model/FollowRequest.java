package linkedin.requestservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FollowRequest {
	
	@Transient
    public static final String SEQUENCE_NAME = "request_sequence";
	
	@Id
    private int id;
	@Field
    private Boolean accepted;
	@Field
    private int fromProfileId;
	@Field
    private int toProfileId;
}
