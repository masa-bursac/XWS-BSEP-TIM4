package linkedin.profileservice.service;

import java.util.List;

import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.ChangePasswordDTO;
import linkedin.profileservice.dto.RegistrationDTO;
import linkedin.profileservice.dto.RegistrationRequestDTO;
import linkedin.profileservice.dto.UserAccessDTO;
import linkedin.profileservice.model.UserInfo;

public interface IAuthService {

	UserAccessDTO login(AuthDTO authDTO);

	Boolean registration(RegistrationDTO registrationDTO);
	
	String getUsername(int id);

	void forgotPassword(String username);

	Boolean changePassword(ChangePasswordDTO request);

	List<RegistrationRequestDTO> getRegistrationRequests();

	void approveRegistrationRequest(int id);

	void denyRegistrationRequest(int id);

	boolean confirmRegistrationRequest(String token);

}
