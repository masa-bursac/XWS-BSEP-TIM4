package linkedin.postservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IPostService {
	
	int save(MultipartFile[] multipartFile, String caption, int userInfoId) throws IOException;

}
