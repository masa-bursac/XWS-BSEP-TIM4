package linkedin.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import linkedin.profileservice.model.Institution;

public interface InstitutionRepository extends JpaRepository<Institution,Integer>{

}
