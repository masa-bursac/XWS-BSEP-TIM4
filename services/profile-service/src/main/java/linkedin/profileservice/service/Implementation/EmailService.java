package linkedin.profileservice.service.Implementation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import linkedin.profileservice.config.EmailContext;
import linkedin.profileservice.model.PasswordToken;
import linkedin.profileservice.model.Token;
import linkedin.profileservice.model.UserInfo;
import linkedin.profileservice.repository.AuthRepository;
import linkedin.profileservice.repository.PasswordTokenRepository;
import linkedin.profileservice.repository.ProfileRepository;
import linkedin.profileservice.service.IEmailService;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements IEmailService {
	
	private final AuthRepository authRepository;
	private final PasswordTokenRepository passwordTokenRepository;
	private final PasswordTokenService passwordTokenService;
	private final EmailContext emailContext;
	
	@Autowired
    public EmailService(AuthRepository authRepository, PasswordTokenRepository passwordTokenRepository, PasswordTokenService passwordTokenService,
    		EmailContext emailContext) {
        this.authRepository = authRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordTokenService = passwordTokenService;
        this.emailContext = emailContext;
    }


	@Override
	public void forgotPassword(String username) {
		
        UserInfo user = authRepository.findOneByUsername(username);
        Date now = new Date();
        PasswordToken passwordToken = passwordTokenRepository.findOneByUsername(username);
        if(passwordToken == null){
            passwordToken = passwordTokenService.createToken(username);
        }else{
            if(passwordToken.getExpiryDate().before(now)){
            	passwordTokenRepository.delete(passwordToken);
                passwordToken = passwordTokenService.createToken(username);
            }
        }

        String title = "Change your password";
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", user.getName(), user.getSurname()));
        context.setVariable("link", String.format("http://localhost:4200/changePassword/%s", passwordToken.getToken()));
        emailContext.send("firma4validation@gmail.com", title, "forgotPassword", context);
	}

}
