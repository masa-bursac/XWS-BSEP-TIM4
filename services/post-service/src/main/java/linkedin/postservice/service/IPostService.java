package linkedin.postservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import linkedin.postservice.dto.CommentDTO;

public interface IPostService {
	
	int save(MultipartFile[] multipartFile, String caption, int userInfoId) throws IOException;

	void like(int userId, int postId);

	void dislike(int userId, int postId);

	Boolean addComment(CommentDTO commentDTO);

}
