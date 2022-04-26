package linkedin.profileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import linkedin.profileservice.model.Skill;

public interface SkillRepository extends JpaRepository<Skill,Integer>{

}
