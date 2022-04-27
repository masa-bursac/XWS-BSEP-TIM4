package linkedin.profileservice.service.Implementation;

import java.awt.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.profileservice.repository.ProfileRepository;
import linkedin.profileservice.service.IProfileService;

@Service
public class ProfileService implements IProfileService{

	private final ProfileRepository profileRepository;
	
	@Autowired
	public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

	
}
