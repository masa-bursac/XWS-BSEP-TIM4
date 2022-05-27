package linkedin.agentservice.service;

import java.util.List;

import linkedin.agentservice.dto.AuthDTO;
import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.dto.RegistrationRequestDTO;
import linkedin.agentservice.dto.UserAccessDTO;

public interface IAgentService {

	Boolean registration(RegistrationDTO registrationDTO);

	UserAccessDTO login(AuthDTO authDTO);

	List<RegistrationRequestDTO> getRegistrationRequests();

	void approveRegistrationRequest(int id);

	void denyRegistrationRequest(int id);

}
