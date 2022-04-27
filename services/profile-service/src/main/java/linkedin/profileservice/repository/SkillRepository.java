package linkedin.profileservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.profileservice.model.Skill;

public interface SkillRepository extends MongoRepository<Skill,Integer>{

}
