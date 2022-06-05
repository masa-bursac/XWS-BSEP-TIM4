package linkedin.agentservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.agentservice.model.AccountStatus;
import linkedin.agentservice.model.UserInfo;

public interface AgentRepository extends MongoRepository<UserInfo,Integer>{

	UserInfo findOneByUsername(String username);
	List<UserInfo> findAllByAccountStatus(AccountStatus pending);
	UserInfo findOneById(int id);
}
