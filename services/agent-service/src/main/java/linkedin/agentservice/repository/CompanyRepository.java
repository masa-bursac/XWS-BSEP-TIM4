package linkedin.agentservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.agentservice.model.Company;
import linkedin.agentservice.model.CompanyStatus;


public interface CompanyRepository extends MongoRepository<Company,Integer>{

	List<Company> findAllByCompanyStatus(CompanyStatus pending);

	Company findOneByCompanyName(String companyName);

	Company findOneByUsername(String username);
}
