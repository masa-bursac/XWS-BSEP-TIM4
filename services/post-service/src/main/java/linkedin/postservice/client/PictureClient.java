package linkedin.postservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import linkedin.postservice.dto.ImageDTO;

@FeignClient(name="picture",url="${app.picture.url}")
public interface PictureClient {
	
	@PostMapping("/picture/upload")
    Integer uploadImage(@RequestBody ImageDTO imageDTO);
	
	@GetMapping("/picture/getContent/{id}")
	byte[] getContent(@PathVariable Integer id);
	
	@GetMapping("/picture/getName/{id}")
	String getName(@PathVariable Integer id);
}
