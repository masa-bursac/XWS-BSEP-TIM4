package linkedin.profileservice.service;

import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.ChangePasswordDTO;
import linkedin.profileservice.dto.RegistrationDTO;
import linkedin.profileservice.dto.UserAccessDTO;
import linkedin.profileservice.model.UserInfo;

public interface IAuthService {

	UserAccessDTO login(AuthDTO authDTO);

	Boolean registration(RegistrationDTO registrationDTO);
	
	String getUsername(int id);

	void forgotPassword(String username);

	Boolean changePassword(ChangePasswordDTO request);

}
