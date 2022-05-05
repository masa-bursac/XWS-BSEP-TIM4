package linkedin.profileservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="request",url="${app.request.url}")
public interface RequestClient {
	@PutMapping("/request/delete/{to}/{from}")
	 void delete(@PathVariable int to, @PathVariable int from);
}
