package linkedin.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import linkedin.profileservice.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile,Integer>{

	Profile save(Profile profile);
    Profile findOneByUserInfoId(int id);
    Profile findOneById(int id);
}
