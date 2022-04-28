package linkedin.profileservice.service.Implementation;

import java.awt.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.profileservice.dto.InstitutionDTO;
import linkedin.profileservice.dto.UpdateDTO;
import linkedin.profileservice.model.Gender;
import linkedin.profileservice.model.Institution;
import linkedin.profileservice.model.Profile;
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
		System.out.println(profile.getId());
		Institution institution = new Institution(institutionDTO);
		institution.setId((int) sequenceGeneratorService.generateSequence(Institution.SEQUENCE_NAME));
		profile.getExperience().add(institution);
		profileRepository.save(profile);
		return true;
	}

	
}
