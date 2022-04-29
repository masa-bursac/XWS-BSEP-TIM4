package linkedin.pictureservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "picture")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image {
	
	@Transient
    public static final String SEQUENCE_NAME = "image_sequence";
	
    @Id
    private int id;
    
    @Field
    private byte[] content;
    
    @Field
    private String name;
    
    @Field
    private String location;
    
    @Field
    private boolean image;
}
