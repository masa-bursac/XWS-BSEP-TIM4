package linkedin.postservice.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
	
	@Transient
    public static final String SEQUENCE_NAME = "post_sequence";
	
	@Id
    private int id;
	
	@Field
	private int idUser;
	
    @Field
    private List<Integer> likeIds;
    
    @Field
    private List<Integer> dislikeIds;

    @Field
    private PostInfo postInfo;
    
    @Field
    private List<Comment> comments;

}
