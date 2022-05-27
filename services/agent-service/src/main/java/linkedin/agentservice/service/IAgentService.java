package linkedin.agentservice.service;

import linkedin.agentservice.dto.AuthDTO;
import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.dto.UserAccessDTO;

public interface IAgentService {

	Boolean registration(RegistrationDTO registrationDTO);

	UserAccessDTO login(AuthDTO authDTO);

}
