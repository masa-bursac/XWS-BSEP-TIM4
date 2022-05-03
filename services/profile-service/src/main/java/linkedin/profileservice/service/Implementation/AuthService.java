package linkedin.profileservice.service.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.RegistrationDTO;
import linkedin.profileservice.dto.UserAccessDTO;
import linkedin.profileservice.model.Institution;
import linkedin.profileservice.model.Profile;
import linkedin.profileservice.model.Skill;
import linkedin.profileservice.model.UserInfo;
import linkedin.profileservice.repository.AuthRepository;
import linkedin.profileservice.repository.ProfileRepository;
import linkedin.profileservice.service.IAuthService;

@Service
public class AuthService implements IAuthService{

	private final AuthRepository authRepository;
	private final ProfileRepository profileRepository;
	static SequenceGeneratorService sequenceGeneratorService;

	@Autowired
    public AuthService(AuthRepository authRepository, ProfileRepository profileRepository, SequenceGeneratorService sg) {
        this.authRepository = authRepository;
        this.profileRepository = profileRepository;
        this.sequenceGeneratorService = sg;
    }

	@Override
	public UserAccessDTO login(AuthDTO authDTO) {
		
		UserInfo user = authRepository.findOneByUsername(authDTO.getUsername());
        if (user == null) {
            return null;
        }
        UserAccessDTO userResponse = new UserAccessDTO(user);
        if(authDTO.getPassword().equals(user.getPassword())) {
            return userResponse;
        }
        else{
            return null;
        }
	}

	@Override
	public Boolean registration(RegistrationDTO registrationDTO) {
		
        UserInfo userInfo = new UserInfo(registrationDTO);
        if(authRepository.findOneByUsername(userInfo.getUsername())!=null) {
        	return false;
        };
        userInfo.setId((int) sequenceGeneratorService.generateSequence(UserInfo.SEQUENCE_NAME));
        authRepository.save(userInfo);
        UserInfo ui = authRepository.findOneByUsername(userInfo.getUsername());
        Profile profile = new Profile(userInfo.getId());
        profile.setId((int) sequenceGeneratorService.generateSequence(Profile.SEQUENCE_NAME));
        profile.setIsPrivate(false);
        
        profile.setEducation(new ArrayList<Institution>());
        profile.setExperience(new ArrayList<Institution>());
        profile.setFollowers(new ArrayList<Integer>());
        profile.setFollowing(new ArrayList<Integer>());
        profile.setInterests(new ArrayList<Skill>());
        profile.setSkills(new ArrayList<Skill>());
        profile.setPostIds(new ArrayList<Integer>());
        
        profileRepository.save(profile);
        return true;
	}

	
}
