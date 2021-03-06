package linkedin.postservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="profile",url="${app.profile.url}")
public interface ProfileClient {
	
	 @PutMapping("/profile/addPost/{postId}/{userInfoId}")
	 Boolean addPost(@PathVariable int postId, @PathVariable int userInfoId);

	 @GetMapping("/profile/getAllPublicIds")
	 List<Integer> getAllPublicIds();

	 @GetMapping("/profile/getFollowingIds/{loggedInId}")
	 List<Integer> getFollowingIds(@PathVariable int loggedInId);

}
