package linkedin.profileservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.profileservice.model.PasswordToken;
import linkedin.profileservice.model.UserInfo;

public interface PasswordTokenRepository extends MongoRepository<PasswordToken,Integer>{

	PasswordToken findOneByUsername(String username);
	PasswordToken findOneByToken(String username);
}
