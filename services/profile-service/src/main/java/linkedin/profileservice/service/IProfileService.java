package linkedin.profileservice.service;

import java.util.List;

import org.springframework.util.MultiValueMap;

import linkedin.profileservice.dto.InstitutionDTO;
import linkedin.profileservice.dto.InstitutionUpdateDTO;
import linkedin.profileservice.dto.SkillDTO;
import linkedin.profileservice.dto.ProfileDTO;
import linkedin.profileservice.dto.UpdateDTO;
import linkedin.profileservice.model.Skill;

public interface IProfileService {

	Boolean update(UpdateDTO userInfo);
	Boolean addExperience(InstitutionDTO institutionDTO);
	Boolean addEducation(InstitutionDTO institutionDTO);
	Boolean addSkill(SkillDTO skillDTO);
	Boolean addInterest(SkillDTO skillDTO);
	Boolean updateInterest(Skill skill);
	Boolean updateSkill(Skill skill);
	Boolean updateExperience(InstitutionUpdateDTO institutionDTO);
	Boolean updateEducation(InstitutionUpdateDTO institutionDTO);
	Boolean deleteExperience(InstitutionUpdateDTO institutionDTO);
	Boolean deleteEducation(InstitutionUpdateDTO institutionDTO);
	Boolean deleteSkill(Skill skill);
	Boolean deleteInterest(Skill skill);
	List<ProfileDTO> getPublicProfiles();
	List<ProfileDTO> getByUsername(String searchUsername);
	Boolean addPost(int postId, int userInfoId);
	List<Integer> getAllPublicIds();
}
