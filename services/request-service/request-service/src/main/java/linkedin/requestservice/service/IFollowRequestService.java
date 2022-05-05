package linkedin.requestservice.service;

import java.util.List;

import linkedin.requestservice.dto.FollowRequestDTO;
import linkedin.requestservice.model.FollowRequest;

public interface IFollowRequestService {
	FollowRequest newRequest(FollowRequest request);
	List<FollowRequestDTO> getAllForProfile(int loggedIn);
	void delete(int to, int from);
}
