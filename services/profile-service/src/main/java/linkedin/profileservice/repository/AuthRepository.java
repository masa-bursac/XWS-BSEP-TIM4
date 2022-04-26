package linkedin.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import linkedin.profileservice.model.UserInfo;

public interface AuthRepository extends JpaRepository<UserInfo,Integer>{

	UserInfo findOneByUsername(String username);
    UserInfo findOneById(int id);
}
