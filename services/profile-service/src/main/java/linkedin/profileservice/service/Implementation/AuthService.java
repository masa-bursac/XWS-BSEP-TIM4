package linkedin.profileservice.service.Implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import linkedin.profileservice.config.GeneralException;
import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.ChangePasswordDTO;
import linkedin.profileservice.dto.RegistrationDTO;
import linkedin.profileservice.dto.RegistrationRequestDTO;
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
    private final PasswordTokenService passwordTokenService;
    private final AttackService attackService;


	@Autowired
    public AuthService(AuthRepository authRepository, ProfileRepository profileRepository, SequenceGeneratorService sg,
    		 Token token, PasswordEncoder passwordEncoder, EmailService emailService, PasswordTokenRepository passwordTokenRepository, PasswordTokenService passwordTokenService, AttackService attackService) {
        this.authRepository = authRepository;
        this.profileRepository = profileRepository;
        this.sequenceGeneratorService = sg;
        this.token = token;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordTokenService = passwordTokenService;
        this.attackService = attackService;
    }

	@Override
	public UserAccessDTO login(AuthDTO authDTO) {
		
		UserInfo user = authRepository.findOneByUsername(authDTO.getUsername());
        if (user == null) {
            return null;
        }
        
        if(user == null) {
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
        Date now = new Date();
        Date dayAfter = new Date(user.getBlockDate().getTime() + (1000 * 60 * 60 * 24));
        

        if(passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
        	if(dayAfter.before(now)) {
        	user.setLoginCounter(0);
            authRepository.save(user);
        	return userResponse;
        	}else 
        	{
        		 throw new GeneralException("You are blocked!", HttpStatus.BAD_REQUEST);
        	}
        }
        else{
        	user.setLoginCounter(user.getLoginCounter()+1);
            authRepository.save(user);
            if(user.getLoginCounter() > 4)
            {
            	user.setBlockDate(now);
            	authRepository.save(user);
            	throw new GeneralException("You have tried to login more then 3 times!", HttpStatus.BAD_REQUEST);
            }

            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }
	}

	@Override
	public Boolean registration(RegistrationDTO registrationDTO) {
		if(!attackService.validateRegistration(registrationDTO))
			throw new GeneralException("Form input is not valid.", HttpStatus.BAD_REQUEST);
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
        userInfo.setBlockDate(new Date(1001-01-01));
        userInfo.setLoginCounter(0);
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
        return userInfo;
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

	@Override
	public List<RegistrationRequestDTO> getRegistrationRequests() {
		List<UserInfo> users = authRepository.findAllByAccountStatus(AccountStatus.PENDING);
        List<RegistrationRequestDTO> regResponses = new ArrayList<>();
        for (UserInfo user: users) {
        	RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        	registrationRequestDTO.setId(user.getId());
        	registrationRequestDTO.setName(user.getName());
        	registrationRequestDTO.setSurname(user.getSurname());
        	registrationRequestDTO.setUsername(user.getUsername());
        	registrationRequestDTO.setEmail(user.getEmail());
        	regResponses.add(registrationRequestDTO);
        }
        return regResponses;
	}

	@Override
	public void approveRegistrationRequest(int id) {
		UserInfo user = authRepository.findOneById(id);
        user.setAccountStatus(AccountStatus.APPROVED);
        UserInfo savedUser = authRepository.save(user);
        passwordTokenService.createToken(user.getUsername());
        emailService.approveRegistrationMail(savedUser);		
	}

	@Override
	public void denyRegistrationRequest(int id) {
		UserInfo user = authRepository.findOneById(id);
        user.setAccountStatus(AccountStatus.DENIED);
        UserInfo savedUser = authRepository.save(user);
        emailService.denyRegistrationMail(savedUser);		
	}

	@Override
	public boolean confirmRegistrationRequest(String token) {
		System.out.println(passwordTokenRepository.findOneByToken(token));
        String username = passwordTokenRepository.findOneByToken(token).getUsername();

        if(username == null){
            return false;
        }
        
        UserInfo user = authRepository.findOneByUsername(username);
        PasswordToken passwordToken = passwordTokenRepository.findOneByUsername(username);
        Date now = new Date();
        if(passwordToken.getExpiryDate().before(now)){
            return false;
        }else {
            user.setAccountStatus(AccountStatus.ACTIVATED);
            authRepository.save(user);
            passwordTokenRepository.delete(passwordToken);
            return true;
        }
	}

	@Override
	public void passwordlessLogin(String username) {
		emailService.passwordlessLogin(username);
	}
	
}
