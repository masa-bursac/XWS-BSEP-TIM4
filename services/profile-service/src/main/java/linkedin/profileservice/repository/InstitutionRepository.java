package linkedin.profileservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.profileservice.model.Institution;

public interface InstitutionRepository extends MongoRepository<Institution,Integer>{

}
