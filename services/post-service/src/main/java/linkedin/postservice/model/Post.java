package linkedin.postservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    @ElementCollection
    @CollectionTable(name="Post_Likes", joinColumns=@JoinColumn(name="Post_ID"))
    @Column(name="like")
    private List<Integer> likeIds;
    
    @ElementCollection
    @CollectionTable(name="Post_Dislikes", joinColumns=@JoinColumn(name="Post_ID"))
    @Column(name="dislike")
    private List<Integer> dislikeIds;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PostInfo postInfo;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Post_ID")
    private List<Comment> comments;

}
