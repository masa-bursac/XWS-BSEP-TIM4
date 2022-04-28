package linkedin.profileservice.service.Implementation;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.profileservice.dto.InstitutionDTO;
import linkedin.profileservice.dto.InstitutionUpdateDTO;
import linkedin.profileservice.dto.ProfileDTO;
import linkedin.profileservice.dto.SkillDTO;
import linkedin.profileservice.dto.UpdateDTO;
import linkedin.profileservice.model.Gender;
import linkedin.profileservice.model.Institution;
import linkedin.profileservice.model.Profile;
import linkedin.profileservice.model.Skill;
import linkedin.profileservice.model.UserInfo;
import linkedin.profileservice.repository.AuthRepository;
import linkedin.profileservice.repository.ProfileRepository;
import linkedin.profileservice.service.IProfileService;

@Service
public class ProfileService implements IProfileService{

	private final ProfileRepository profileRepository;
	private final AuthRepository authRepository;
	static SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	public ProfileService(ProfileRepository profileRepository, AuthRepository ar,SequenceGeneratorService sg)
    {
        this.profileRepository = profileRepository;
        this.authRepository = ar;
        this.sequenceGeneratorService = sg;
    }
	
	@Override
	public Boolean update(UpdateDTO userInfo) {
		UserInfo userForUpdating = authRepository.findOneById(userInfo.getId());
		userForUpdating.setEmail(userInfo.getEmail());
		if(userInfo.getGender().toLowerCase().equals(Gender.Male.toString().toLowerCase(Locale.ROOT)))
			userForUpdating.setGender(Gender.Male);
        else if(userInfo.getGender().toLowerCase().equals(Gender.Female.toString().toLowerCase(Locale.ROOT)))
        	userForUpdating.setGender(Gender.Female);
        else
        	userForUpdating.setGender(Gender.NonBinary);
		
		String[] array = userInfo.getDateOfBirth().split("T");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(array[0],formatter);
        userForUpdating.setDateOfBirth(dateTime);
		userForUpdating.setPhone(userInfo.getPhone());
		userForUpdating.setSurname(userInfo.getSurname());
		userForUpdating.setName(userInfo.getName());
		
		if(authRepository.findOneByUsername(userInfo.getUsername())!=null) {
        	if(!userInfo.getUsername().equals(userForUpdating.getUsername()))
        		return false;
        };
    	userForUpdating.setUsername(userInfo.getUsername());

		Profile profile = profileRepository.findOneByUserInfoId(userInfo.getId());
		profile.setBiography(userInfo.getBiography());
		
        if (authRepository.save(userForUpdating) != null || profileRepository.save(profile) != null)
            return true;
        else
            return false;
        
	}

	@Override
	public Boolean addExperience(InstitutionDTO institutionDTO) {
		Profile profile =  profileRepository.findOneByUserInfoId(institutionDTO.getUserInfoId());
		Institution institution = new Institution(institutionDTO);
		institution.setId((int) sequenceGeneratorService.generateSequence(Institution.SEQUENCE_NAME));
		profile.getExperience().add(institution);
		profileRepository.save(profile);
		return true;
	}
	
	@Override
	public Boolean addEducation(InstitutionDTO institutionDTO) {
		Profile profile =  profileRepository.findOneByUserInfoId(institutionDTO.getUserInfoId());
		Institution institution = new Institution(institutionDTO);
		institution.setId((int) sequenceGeneratorService.generateSequence(Institution.SEQUENCE_NAME));
		profile.getEducation().add(institution);
		profileRepository.save(profile);
		return true;
	}

	@Override
	public Boolean addSkill(SkillDTO skillDTO) {
		Profile profile =  profileRepository.findOneByUserInfoId(skillDTO.getUserInfoId());
		Skill skill = new Skill(skillDTO);
		skill.setId((int) sequenceGeneratorService.generateSequence(Skill.SEQUENCE_NAME));
		profile.getSkills().add(skill);
		profileRepository.save(profile);
		return true;
	}

	@Override
	public Boolean addInterest(SkillDTO skillDTO) {
		Profile profile =  profileRepository.findOneByUserInfoId(skillDTO.getUserInfoId());
		Skill skill = new Skill(skillDTO);
		skill.setId((int) sequenceGeneratorService.generateSequence(Skill.SEQUENCE_NAME));
		profile.getInterests().add(skill);
		profileRepository.save(profile);
		return true;
	}

	@Override
	public Boolean updateInterest(Skill skill) {
		// TODO Auto-generated method stub
		Profile profile =  profileRepository.findOneByUserInfoId(skill.getUserInfoId());
		for(int i=0; i<profile.getInterests().size();i++) {
			if(profile.getInterests().get(i).getId() == skill.getId()) {
				profile.getInterests().get(i).setName(skill.getName());
				profile.getInterests().get(i).setOtherInfo(skill.getOtherInfo());
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public Boolean updateSkill(Skill skill) {
		// TODO Auto-generated method stub
		Profile profile =  profileRepository.findOneByUserInfoId(skill.getUserInfoId());
		for(int i=0; i<profile.getSkills().size();i++) {
			if(profile.getSkills().get(i).getId() == skill.getId()) {
				profile.getSkills().get(i).setName(skill.getName());
				profile.getSkills().get(i).setOtherInfo(skill.getOtherInfo());
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public Boolean updateExperience(InstitutionUpdateDTO institutionDTO) {
		
		Profile profile =  profileRepository.findOneByUserInfoId(institutionDTO.getUserInfoId());
		Institution institution = new Institution(institutionDTO);

		for(int i=0; i<profile.getExperience().size();i++) {
			if(profile.getExperience().get(i).getId() == institution.getId()) {
				profile.getExperience().get(i).setName(institution.getName());
				profile.getExperience().get(i).setPosition(institution.getPosition());
				profile.getExperience().get(i).setStart(institution.getStart());
				profile.getExperience().get(i).setEnd(institution.getEnd());
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public Boolean updateEducation(InstitutionUpdateDTO institutionDTO) {
		
		Profile profile =  profileRepository.findOneByUserInfoId(institutionDTO.getUserInfoId());
		Institution institution = new Institution(institutionDTO);

		for(int i=0; i<profile.getEducation().size();i++) {
			if(profile.getEducation().get(i).getId() == institution.getId()) {
				profile.getEducation().get(i).setName(institution.getName());
				profile.getEducation().get(i).setPosition(institution.getPosition());
				profile.getEducation().get(i).setStart(institution.getStart());
				profile.getEducation().get(i).setEnd(institution.getEnd());
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public Boolean deleteExperience(InstitutionUpdateDTO institutionDTO) {
		Profile profile =  profileRepository.findOneByUserInfoId(institutionDTO.getUserInfoId());
		Institution institution = new Institution(institutionDTO);
		for(int i=0; i<profile.getExperience().size();i++) {
			if(profile.getExperience().get(i).getId() == institution.getId()) {
				profile.getExperience().remove(i);
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public Boolean deleteEducation(InstitutionUpdateDTO institutionDTO) {
		Profile profile =  profileRepository.findOneByUserInfoId(institutionDTO.getUserInfoId());
		Institution institution = new Institution(institutionDTO);
		for(int i=0; i<profile.getEducation().size();i++) {
			if(profile.getEducation().get(i).getId() == institution.getId()) {
				profile.getEducation().remove(i);
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public Boolean deleteSkill(Skill skill) {
		Profile profile =  profileRepository.findOneByUserInfoId(skill.getUserInfoId());
		for(int i=0; i<profile.getSkills().size();i++) {
			if(profile.getSkills().get(i).getId() == skill.getId()) {
				profile.getSkills().remove(i);
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public Boolean deleteInterest(Skill skill) {
		Profile profile =  profileRepository.findOneByUserInfoId(skill.getUserInfoId());
		for(int i=0; i<profile.getInterests().size();i++) {
			if(profile.getInterests().get(i).getId() == skill.getId()) {
				profile.getInterests().remove(i);
			}
		}
        if (profileRepository.save(profile) != null)
            return true;
        else
            return false;
	}

	@Override
	public List<ProfileDTO> getPublicProfiles() {
		// TODO Auto-generated method stub
		List<Profile> profiles =  profileRepository.findAll();
		List<Integer> userInfoIds = new ArrayList();
		List<ProfileDTO> profileDTOs = new ArrayList();
		for(int i=0; i<profiles.size();i++) {
			if(!profiles.get(i).getIsPrivate()) {
				userInfoIds.add(profiles.get(i).getUserInfo());
			}
		}
		for(int i=0; i<userInfoIds.size();i++) {
			UserInfo profile =  authRepository.findOneById(userInfoIds.get(i));
			profileDTOs.add(new ProfileDTO(profile.getId(),profile.getName(),profile.getSurname(),profile.getUsername()));
		}
		
		return profileDTOs;
	}

	@Override
	public List<ProfileDTO> getByUsername(String searchUsername) {
		
		List<Profile> profiles =  profileRepository.findAll();
		List<Integer> userInfoIds = new ArrayList();
		List<ProfileDTO> profileDTOs = new ArrayList();
		for(int i=0; i<profiles.size();i++) {
			if(!profiles.get(i).getIsPrivate()) {
				userInfoIds.add(profiles.get(i).getUserInfo());
			}
		}
		for(int i=0; i<userInfoIds.size();i++) {
			UserInfo profile =  authRepository.findOneById(userInfoIds.get(i));
			if(profile.getUsername().contains(searchUsername)) {
				profileDTOs.add(new ProfileDTO(profile.getId(),profile.getName(),profile.getSurname(),profile.getUsername()));
			}
		}
		
		return profileDTOs;
	}

	
}
