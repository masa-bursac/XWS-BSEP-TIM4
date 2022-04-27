package linkedin.profileservice.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.UserAccessDTO;
import linkedin.profileservice.model.UserInfo;
import linkedin.profileservice.repository.AuthRepository;
import linkedin.profileservice.service.IAuthService;

@Service
public class AuthService implements IAuthService{

	private final AuthRepository authRepository;
	
	@Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

	@Override
	public UserAccessDTO login(AuthDTO authDTO) {
		UserInfo user = authRepository.findOneByUsername(authDTO.getUsername());
        if (user == null) {
            return null;
        }
        UserAccessDTO userResponse = new UserAccessDTO(user);
        if(authDTO.getPassword().equals(user.getPassword())) {
            return userResponse;
        }
        else{
            return null;
        }
	}
}
