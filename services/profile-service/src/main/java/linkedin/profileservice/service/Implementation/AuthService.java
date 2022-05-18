package linkedin.profileservice.service.Implementation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import linkedin.profileservice.config.GeneralException;
import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.ChangePasswordDTO;
import linkedin.profileservice.dto.RegistrationDTO;
import linkedin.profileservice.dto.UserAccessDTO;
import linkedin.profileservice.model.AccountStatus;
import linkedin.profileservice.model.Institution;
import linkedin.profileservice.model.PasswordToken;
import linkedin.profileservice.model.Profile;
import linkedin.profileservice.model.Roles;
import linkedin.profileservice.model.Skill;
import linkedin.profileservice.model.Token;
import linkedin.profileservice.model.UserInfo;
import linkedin.profileservice.repository.AuthRepository;
import linkedin.profileservice.repository.PasswordTokenRepository;
import linkedin.profileservice.repository.ProfileRepository;
import linkedin.profileservice.service.IAuthService;

@Service
public class AuthService implements IAuthService{

	private final AuthRepository authRepository;
	private final ProfileRepository profileRepository;
	static SequenceGeneratorService sequenceGeneratorService;
    private final Token token;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PasswordTokenRepository passwordTokenRepository;


	@Autowired
    public AuthService(AuthRepository authRepository, ProfileRepository profileRepository, SequenceGeneratorService sg,
    		 Token token, PasswordEncoder passwordEncoder, EmailService emailService, PasswordTokenRepository passwordTokenRepository) {
        this.authRepository = authRepository;
        this.profileRepository = profileRepository;
        this.sequenceGeneratorService = sg;
        this.token = token;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.passwordTokenRepository = passwordTokenRepository;
    }

	@Override
	public UserAccessDTO login(AuthDTO authDTO) {
		
		UserInfo user = authRepository.findOneByUsername(authDTO.getUsername());
        if (user == null) {
            return null;
        }
        
        if(user == null || !passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }
        if(user != null && user.getAccountStatus().equals(AccountStatus.PENDING)){
            throw new GeneralException("Your registration hasn't been approved yet.", HttpStatus.BAD_REQUEST);
        }
        if(user != null && user.getAccountStatus().equals(AccountStatus.DENIED)){
            throw new GeneralException("Your registration has been denied.", HttpStatus.BAD_REQUEST);
        }
        if(user != null && user.getAccountStatus().equals(AccountStatus.APPROVED)){
            throw new GeneralException("Your registration has been approved by admin. Please activate your account.", HttpStatus.BAD_REQUEST);
        }
        
        String jwt = token.generateToken(user);
        int expiresIn = token.getEXPIRES_IN();

        UserAccessDTO userResponse = new UserAccessDTO(user,jwt);
        userResponse.setTokenExpiresIn(expiresIn);

        if(passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            return userResponse;
        }
        else{
            return null;
        }
	}

	@Override
	public Boolean registration(RegistrationDTO registrationDTO) {
		
		if(!registrationDTO.getPassword().equals(registrationDTO.getRepeatPassword())){
            throw new GeneralException("Passwords do not match.", HttpStatus.BAD_REQUEST);
        }
		
        registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        UserInfo userInfo = new UserInfo(registrationDTO);
        if(authRepository.findOneByUsername(userInfo.getUsername())!=null) {
        	return false;
        };
        
        userInfo.setRole(Roles.USER);
        userInfo.setAccountStatus(AccountStatus.PENDING);
        userInfo.setId((int) sequenceGeneratorService.generateSequence(UserInfo.SEQUENCE_NAME));
        authRepository.save(userInfo);

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
	@Override
	public String getUsername(int id) {
		 
	    UserInfo ui = authRepository.findOneById(id);
	    return ui.getUsername();
	}

	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
        UserInfo userInfo = authRepository.findOneByUsername(username);
        return (UserDetails) userInfo;
	}

	@Override
	public void forgotPassword(String username) {
		emailService.forgotPassword(username);		
	}

	@Override
	public Boolean changePassword(ChangePasswordDTO request) {
		
		if(!request.getPassword().equals(request.getRePassword())){
            return false;
        }
		
        PasswordToken passwordToken = passwordTokenRepository.findOneByToken(request.getUsername());
        UserInfo user = authRepository.findOneByUsername(passwordToken.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        authRepository.save(user);
        passwordTokenRepository.delete(passwordToken);
        return true;
		
	}
	
}
