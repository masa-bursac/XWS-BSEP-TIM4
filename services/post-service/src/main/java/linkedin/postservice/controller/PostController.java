package linkedin.postservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import linkedin.postservice.dto.CommentDTO;
import linkedin.postservice.model.Post;
import linkedin.postservice.service.IPostService;

@RestController
@RequestMapping("/post")
public class PostController {

	private final IPostService postService;
	
    public PostController(IPostService postService){this.postService = postService;}
    
    @PostMapping("/create")
    public Integer createPost(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("caption") String caption,
                              @RequestParam("userInfoId") int userInfoId) throws Exception{
        return postService.save(multipartFile, caption, userInfoId);
    }
    
    @PutMapping("/like/{userId}/{postId}")
    public void like(@PathVariable int userId, @PathVariable int postId){
         postService.like(userId,postId);
    }
    
    @PutMapping("/dislike/{userId}/{postId}")
    public void dislike(@PathVariable int userId, @PathVariable int postId){
        postService.dislike(userId,postId);
    }
    
    @PutMapping("/addComment")
    public ResponseEntity addComment(@RequestBody CommentDTO commentDTO){
    	try{
            return new ResponseEntity(postService.addComment(commentDTO), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @GetMapping("/getAllPublic")
    public List<Post> getAllPublic() {
        return postService.getAllPublic();
    }

}
