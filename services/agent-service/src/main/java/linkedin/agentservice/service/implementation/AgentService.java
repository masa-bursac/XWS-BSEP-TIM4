package linkedin.agentservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.dto.AuthDTO;
import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.dto.UserAccessDTO;
import linkedin.agentservice.model.AccountStatus;
import linkedin.agentservice.model.Roles;
import linkedin.agentservice.model.Token;
import linkedin.agentservice.model.UserInfo;
import linkedin.agentservice.repository.AgentRepository;
import linkedin.agentservice.service.IAgentService;


@Service
public class AgentService implements IAgentService{
	
	static SequenceGeneratorService sequenceGeneratorService;
	private final PasswordEncoder passwordEncoder;
	private final AgentRepository agentRepository;
	private final Token token;
	
	@Autowired
    public AgentService(SequenceGeneratorService sequenceGeneratorService,PasswordEncoder passwordEncoder, AgentRepository agentRepository, Token token) {
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.passwordEncoder = passwordEncoder;
		this.agentRepository = agentRepository;
		this.token = token;
    }

	@Override
	public Boolean registration(RegistrationDTO registrationDTO) {
		
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
        
        String jwt = token.generateToken(user);
        int expiresIn = token.getEXPIRES_IN();

        UserAccessDTO userResponse = new UserAccessDTO(user,jwt);
        userResponse.setTokenExpiresIn(expiresIn);
        
        return userResponse;
        
	}

}
