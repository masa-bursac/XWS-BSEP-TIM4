package linkedin.postservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.postservice.service.IPostService;

@RestController
@RequestMapping("/post")
public class PostController {

	private final IPostService postService;
	
    public PostController(IPostService postService){this.postService = postService;}
}
