package linkedin.postservice.service.implementation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import linkedin.postservice.client.PictureClient;
import linkedin.postservice.client.ProfileClient;
import linkedin.postservice.dto.CommentDTO;
import linkedin.postservice.dto.ImageDTO;
import linkedin.postservice.dto.PostDTO;
import linkedin.postservice.model.Comment;
import linkedin.postservice.model.Post;
import linkedin.postservice.model.PostInfo;
import linkedin.postservice.repository.PostRepository;
import linkedin.postservice.service.IPostService;

@Service
public class PostService implements IPostService{

	private final PostRepository postRepository;
	private final ProfileClient profileClient;
    private final PictureClient pictureClient;
	static SequenceGeneratorService sequenceGeneratorService;
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	
	@Autowired
    public PostService(PostRepository postRepository, ProfileClient profileClient, PictureClient pictureClient, SequenceGeneratorService sequenceGeneratorService){
        this.postRepository = postRepository;
        this.profileClient = profileClient;
        this.pictureClient = pictureClient;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

	@Override
	public int save(MultipartFile[] multipartFile, String caption, int userInfoId) throws IOException {
		Post p = new Post();
        List<Integer> ids = new ArrayList<>();

        for(MultipartFile file : multipartFile){
            int contentId;
            if(file.getContentType().contains("image"))
                contentId = pictureClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), true));
            else
                contentId = pictureClient.uploadImage(new ImageDTO(file.getOriginalFilename(),file.getBytes(), false));
            ids.add(contentId);
        }

        PostInfo postInfo = new PostInfo();
        postInfo.setPicture(ids);
        postInfo.setCaption(caption);
        postInfo.setDate(LocalDate.now());
        p.setPostInfo(postInfo);
        
        p.setIdUser(userInfoId);
        p.setId((int) sequenceGeneratorService.generateSequence(Post.SEQUENCE_NAME));
        
        Post new_post = postRepository.save(p);
        profileClient.addPost(new_post.getId(), userInfoId);
        
        return new_post.getId();
	}

	@Override
	public void like(int userId, int postId) {
		Post post = postRepository.findOneById(postId);
		
		if(post.getDislikeIds() != null) {
			for(int i= 0; i < post.getDislikeIds().size(); i++)
	        {
	            if(post.getDislikeIds().get(i) == userId)
	                post.getDislikeIds().remove(i);
	        }
		}
        
		if(post.getLikeIds() != null) {
			post.getLikeIds().add(userId);
			logger.info("User with id: " + userId + " liked post with id: " + postId);
		}else {
			List<Integer> likeIds = new ArrayList<>();
			likeIds.add(userId);
			post.setLikeIds(likeIds);
		}
        
        postRepository.save(post);
	}

	@Override
	public void dislike(int userId, int postId) {
		Post post = postRepository.findOneById(postId);
		
		if(post.getLikeIds() != null) {
			for(int i= 0; i < post.getLikeIds().size(); i++)
	        {
	            if(post.getLikeIds().get(i) == userId)
	                post.getLikeIds().remove(i);
	        }
		}
        
		if(post.getDislikeIds() != null) {
			post.getDislikeIds().add(userId);
			logger.info("User with id: " + userId + " disliked post with id: " + postId);
		}else {
			List<Integer> dislikeIds = new ArrayList<>();
			dislikeIds.add(userId);
			post.setDislikeIds(dislikeIds);
		}
        
        postRepository.save(post);
		
	}

	@Override
	public Boolean addComment(CommentDTO commentDTO) {
		Post post = postRepository.findOneById(commentDTO.getPostId());	
		if(post.getComments() != null) {
			post.getComments().add(new Comment(commentDTO.getProfileId(),commentDTO.getContent()));
		}else {
			List<Comment> comments = new ArrayList<>();
			comments.add(new Comment(commentDTO.getProfileId(),commentDTO.getContent()));
			post.setComments(comments);
		}
        
        postRepository.save(post);
        logger.info("User with id: " + commentDTO.getProfileId() + " added comment");
        return true;
	}

	@Override
	public List<PostDTO> getAllPublic() {
		// TODO Auto-generated method stub
		List<Integer> publicProfileIds = profileClient.getAllPublicIds();
		List<PostDTO> publicPosts = new ArrayList<>();
		List<Post> posts= postRepository.findAll();
		PostDTO postDTO = new PostDTO();
		for(int i = 0;i < posts.size(); i++) {
			for(int j=0; j<publicProfileIds.size();j++) {
				if(posts.get(i).getIdUser() == publicProfileIds.get(j)) {
					postDTO = new PostDTO(posts.get(i));
					postDTO.setContent(pictureClient.getContent(posts.get(i).getPostInfo().getPicture().get(0)));
					postDTO.setName(pictureClient.getName(posts.get(i).getPostInfo().getPicture().get(0)));
					publicPosts.add(postDTO);
				}
			}
		}
		return publicPosts;
	}

	@Override
	public List<PostDTO> getFollowingProfilesPosts(int loggedInId) {
		// TODO Auto-generated method stub
		List<Integer> followingIds = profileClient.getFollowingIds(loggedInId);
		
		List<PostDTO> followingPosts = new ArrayList<>();
		List<Post> posts= postRepository.findAll();
		PostDTO postDTO = new PostDTO();
		for(int i = 0;i < posts.size(); i++) {
			for(int j=0; j<followingIds.size();j++) {
				if(posts.get(i).getIdUser() == followingIds.get(j)) {
					postDTO = new PostDTO(posts.get(i));
					postDTO.setContent(pictureClient.getContent(posts.get(i).getPostInfo().getPicture().get(0)));
					postDTO.setName(pictureClient.getName(posts.get(i).getPostInfo().getPicture().get(0)));
					followingPosts.add(postDTO);
				}
			}
		}
		return followingPosts;
	}

	@Override
	public List<PostDTO> getAllPosts(int id) {
		List<PostDTO> userPosts = new ArrayList<>();
		List<Post> posts= postRepository.findAll();
		PostDTO postDTO = new PostDTO();
		for(int i = 0;i < posts.size(); i++) {
			if(posts.get(i).getIdUser() == id) {
				postDTO = new PostDTO(posts.get(i));
				postDTO.setContent(pictureClient.getContent(posts.get(i).getPostInfo().getPicture().get(0)));
				postDTO.setName(pictureClient.getName(posts.get(i).getPostInfo().getPicture().get(0)));
				userPosts.add(postDTO);
			}
		}
		return userPosts;
	}
	
	
}
