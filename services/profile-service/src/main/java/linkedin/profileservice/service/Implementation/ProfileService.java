package linkedin.profileservice.service.Implementation;

import org.springframework.stereotype.Service;

import linkedin.profileservice.repository.ProfileRepository;
import linkedin.profileservice.service.IProfileService;

@Service
public class ProfileService implements IProfileService{

	private final ProfileRepository profileRepository;
	
	public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }
}
