package linkedin.agentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.agentservice.model.UserInfo;

public interface AgentRepository extends MongoRepository<UserInfo,Integer>{

	UserInfo findOneByUsername(String username);
}
