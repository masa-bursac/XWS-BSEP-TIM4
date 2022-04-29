package linkedin.postservice.service.implementation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import linkedin.postservice.client.PictureClient;
import linkedin.postservice.client.ProfileClient;
import linkedin.postservice.dto.ImageDTO;
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
	
	
}
