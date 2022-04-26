package linkedin.profileservice.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.profileservice.repository.AuthRepository;
import linkedin.profileservice.service.IAuthService;

@Service
public class AuthService implements IAuthService{

	private final AuthRepository authRepository;
	
	@Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
}
