package linkedin.requestservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import linkedin.requestservice.model.FollowRequest;

public interface FollowRequestRepository extends MongoRepository<FollowRequest,Integer> {
	
}
