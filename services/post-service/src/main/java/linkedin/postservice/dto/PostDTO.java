package linkedin.postservice.dto;

import java.util.List;


import linkedin.postservice.model.Comment;
import linkedin.postservice.model.Post;
import linkedin.postservice.model.PostInfo;

public class PostDTO {

	private int id;
	
	private int idUser;
	
    private List<Integer> likeIds;
    
    private List<Integer> dislikeIds;

    private PostInfo postInfo;
    
    private List<Comment> comments;
    
    private byte[] content;
    
    private String name;
    
    
    
    
    public PostDTO() {
		super();
	}


	public PostDTO(Post post) {
		this.id = post.getId();
		this.idUser = post.getIdUser();
		this.likeIds = post.getLikeIds();
		this.dislikeIds = post.getDislikeIds();
		this.postInfo = post.getPostInfo();
		this.comments = post.getComments();
	}
    

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public List<Integer> getLikeIds() {
		return likeIds;
	}

	public void setLikeIds(List<Integer> likeIds) {
		this.likeIds = likeIds;
	}

	public List<Integer> getDislikeIds() {
		return dislikeIds;
	}

	public void setDislikeIds(List<Integer> dislikeIds) {
		this.dislikeIds = dislikeIds;
	}

	public PostInfo getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(PostInfo postInfo) {
		this.postInfo = postInfo;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		this.content = content;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
    
    
}
