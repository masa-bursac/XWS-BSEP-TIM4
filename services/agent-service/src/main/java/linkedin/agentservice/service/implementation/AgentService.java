package linkedin.agentservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.model.AccountStatus;
import linkedin.agentservice.model.Roles;
import linkedin.agentservice.model.UserInfo;
import linkedin.agentservice.repository.AgentRepository;
import linkedin.agentservice.service.IAgentService;


@Service
public class AgentService implements IAgentService{
	
	static SequenceGeneratorService sequenceGeneratorService;
	private final PasswordEncoder passwordEncoder;
	private final AgentRepository agentRepository;
	
	@Autowired
    public AgentService(SequenceGeneratorService sequenceGeneratorService,PasswordEncoder passwordEncoder, AgentRepository agentRepository) {
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.passwordEncoder = passwordEncoder;
		this.agentRepository = agentRepository;
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

}
