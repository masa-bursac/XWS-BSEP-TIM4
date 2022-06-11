package linkedin.agentservice.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.dto.AuthDTO;
import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.dto.RegistrationRequestDTO;
import linkedin.agentservice.dto.UserAccessDTO;
import linkedin.agentservice.model.AccountStatus;
import linkedin.agentservice.model.PasswordToken;
import linkedin.agentservice.model.Roles;
import linkedin.agentservice.model.Token;
import linkedin.agentservice.model.UserInfo;
import linkedin.agentservice.repository.AgentRepository;
import linkedin.agentservice.repository.PasswordTokenRepository;
import linkedin.agentservice.service.IAgentService;
import linkedin.agentservice.dto.ChangePasswordDTO;


@Service
public class AgentService implements IAgentService{
	
	static SequenceGeneratorService sequenceGeneratorService;
	private final PasswordEncoder passwordEncoder;
	private final AgentRepository agentRepository;
	private final Token token;
	private final PasswordTokenService passwordTokenService;
	private final PasswordTokenRepository passwordTokenRepository;
	private final EmailService emailService;
	private final AttackService attackService;
    private final Logger logger = LoggerFactory.getLogger(AgentService.class);

	
	@Autowired
    public AgentService(SequenceGeneratorService sequenceGeneratorService,PasswordEncoder passwordEncoder, AgentRepository agentRepository, Token token,
    		PasswordTokenService passwordTokenService, PasswordTokenRepository passwordTokenRepository, EmailService emailService, AttackService attackService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.passwordEncoder = passwordEncoder;
		this.agentRepository = agentRepository;
		this.token = token;
		this.passwordTokenService = passwordTokenService;
		this.passwordTokenRepository = passwordTokenRepository;
		this.emailService = emailService;
		this.attackService = attackService;
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
        if(agentRepository.findOneByUsername(userInfo.getUsername())!=null) {
        	return false;
        };
        
        userInfo.setRole(Roles.USER);
        userInfo.setAccountStatus(AccountStatus.PENDING);
        userInfo.setBlockDate(new Date(1001-01-01));
        userInfo.setLoginCounter(0);
        userInfo.setId((int) sequenceGeneratorService.generateSequence(UserInfo.SEQUENCE_NAME));
        agentRepository.save(userInfo);
       
        return true;
	}

	@Override
	public UserAccessDTO login(AuthDTO authDTO) {
		UserInfo user = agentRepository.findOneByUsername(authDTO.getUsername());
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
        
    	logger.info("User " + authDTO.getUsername() + " has successfully logged in");

        String jwt = token.generateToken(user);
        int expiresIn = token.getEXPIRES_IN();

        UserAccessDTO userResponse = new UserAccessDTO(user,jwt);
        userResponse.setTokenExpiresIn(expiresIn);
        
        Date now = new Date();
        Date dayAfter = new Date(user.getBlockDate().getTime() + (1000 * 60 * 60 * 24));
        
        if(passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
        	if(dayAfter.before(now)) {
        		
        	logger.info("User " + authDTO.getUsername() + " has successfully logged in");
        	user.setLoginCounter(0);
            agentRepository.save(user);
        	return userResponse;
        	}else 
        	{
        		logger.warn("User " + user.getUsername() + " is blocked");
        		 throw new GeneralException("You are blocked!", HttpStatus.BAD_REQUEST);
        	}
        }
        else{
        	user.setLoginCounter(user.getLoginCounter()+1);
            agentRepository.save(user);
            if(user.getLoginCounter() > 4)
            {
            	user.setBlockDate(now);
            	agentRepository.save(user);
            	logger.warn("User " + user.getUsername() + " has entered bad password " + user.getLoginCounter() + " times");
            	throw new GeneralException("You have tried to login more then 4 times!", HttpStatus.BAD_REQUEST);
            }

            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }
        
	}

	@Override
	public List<RegistrationRequestDTO> getRegistrationRequests() {
		List<UserInfo> users = agentRepository.findAllByAccountStatus(AccountStatus.PENDING);
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
		UserInfo user = agentRepository.findOneById(id);
        user.setAccountStatus(AccountStatus.APPROVED);
        UserInfo savedUser = agentRepository.save(user);
        passwordTokenService.createToken(user.getUsername());
        emailService.approveRegistrationMail(savedUser);		
	}

	@Override
	public void denyRegistrationRequest(int id) {
		UserInfo user = agentRepository.findOneById(id);
        user.setAccountStatus(AccountStatus.DENIED);
        UserInfo savedUser = agentRepository.save(user);
        emailService.denyRegistrationMail(savedUser);
		
	}

	@Override
	public Boolean confirmRegistrationRequest(String token) {
		String username = passwordTokenRepository.findOneByToken(token).getUsername();

        if(username == null){
            return false;
        }
        
        UserInfo user = agentRepository.findOneByUsername(username);
        PasswordToken passwordToken = passwordTokenRepository.findOneByUsername(username);
        Date now = new Date();
        if(passwordToken.getExpiryDate().before(now)){
            return false;
        }else {
            user.setAccountStatus(AccountStatus.ACTIVATED);
            agentRepository.save(user);
            passwordTokenRepository.delete(passwordToken);
            return true;
        }
	}

	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		UserInfo userInfo = agentRepository.findOneByUsername(username);
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
        UserInfo user = agentRepository.findOneByUsername(passwordToken.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        agentRepository.save(user);
        passwordTokenRepository.delete(passwordToken);
        return true;
		
	}
	
	@Override
	public void passwordlessLogin(String username) {
		emailService.passwordlessLogin(username);
	}

}
