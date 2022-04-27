package linkedin.profileservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.profileservice.model.Profile;

public interface ProfileRepository extends MongoRepository<Profile,Integer>{

	Profile save(Profile profile);
    Profile findOneByUserInfoId(int id);
    Profile findOneById(int id);
}
