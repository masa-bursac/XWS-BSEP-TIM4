package linkedin.profileservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.profileservice.model.AccountStatus;
import linkedin.profileservice.model.UserInfo;

public interface AuthRepository extends MongoRepository<UserInfo,Integer>{

	UserInfo findOneByUsername(String username);
    UserInfo findOneById(int id);
	List<UserInfo> findAllByAccountStatus(AccountStatus pending);
}
