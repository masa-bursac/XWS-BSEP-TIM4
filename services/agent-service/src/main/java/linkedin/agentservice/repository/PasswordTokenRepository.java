package linkedin.agentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.agentservice.model.PasswordToken;

public interface PasswordTokenRepository extends MongoRepository<PasswordToken,Integer>{

	PasswordToken findOneByUsername(String username);
	PasswordToken findOneByToken(String username);
}
