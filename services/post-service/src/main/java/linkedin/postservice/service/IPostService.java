package linkedin.postservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IPostService {
	
	int save(MultipartFile[] multipartFile, String caption, int userInfoId) throws IOException;

	void like(int userId, int postId);

	void dislike(int userId, int postId);

}
