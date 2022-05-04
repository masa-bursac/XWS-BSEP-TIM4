package linkedin.requestservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="auth",url="${app.auth.url}")
public interface ProfileClient {
	
	@GetMapping("/auth/getUsername/{userInfoId}")
    String getUsername(@PathVariable int userInfoId);
}
