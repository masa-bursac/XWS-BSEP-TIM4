package linkedin.postservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.postservice.repository.PostRepository;
import linkedin.postservice.service.IPostService;

@Service
public class PostService implements IPostService{

	private final PostRepository postRepository;
	
	@Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }
}
