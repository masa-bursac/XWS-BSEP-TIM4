package linkedin.agentservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.agentservice.model.Company;


public interface CompanyRepository extends MongoRepository<Company,Integer>{

}
