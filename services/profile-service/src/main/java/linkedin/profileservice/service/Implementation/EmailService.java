package linkedin.profileservice.service.Implementation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import linkedin.profileservice.config.EmailContext;
import linkedin.profileservice.config.GeneralException;
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
	private final Token token;
	
	@Autowired
    public EmailService(AuthRepository authRepository, PasswordTokenRepository passwordTokenRepository, PasswordTokenService passwordTokenService,
    		EmailContext emailContext, Token token) {
        this.authRepository = authRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordTokenService = passwordTokenService;
        this.emailContext = emailContext;
        this.token = token;
    }


	@Override
	public void forgotPassword(String username) {
		
        UserInfo user = authRepository.findOneByUsername(username);
        if(user == null) {
        	throw new GeneralException("Invalid username", HttpStatus.BAD_REQUEST);
        }
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
        context.setVariable("link", String.format("https://localhost:4200/changePassword/%s", passwordToken.getToken()));
        emailContext.send("firma4validation@gmail.com", title, "forgotPassword", context);
	}


	public void approveRegistrationMail(UserInfo savedUser) {

        String title = "Your registration has been approved.";
        PasswordToken passwordToken = passwordTokenRepository.findOneByUsername(savedUser.getUsername());

        Context context = new Context();
        context.setVariable("name", String.format("%s %s", savedUser.getName(), savedUser.getSurname()));
        context.setVariable("link", String.format("https://localhost:4200/login/%s", passwordToken.getToken()));
        emailContext.send("firma4validation@gmail.com", title, "approveRegistration", context);
		
	}


	public void denyRegistrationMail(UserInfo savedUser) {

        String title = "Your registration has been denied.";
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", savedUser.getName(), savedUser.getSurname()));
        emailContext.send("firma4validation@gmail.com", title, "denyRegistration", context);		
	}


	public void passwordlessLogin(String username) {
		
		UserInfo user = authRepository.findOneByUsername(username);
		if(user == null) {
        	throw new GeneralException("Invalid username", HttpStatus.BAD_REQUEST);
        }
		
		String jwt = token.generateToken(user);

        String title = "Passwordless login";
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", user.getName(), user.getSurname()));
        context.setVariable("link", String.format("https://localhost:4200/homePage/%s", jwt));
        emailContext.send("firma4validation@gmail.com", title, "passwordlessLogin", context);
	}

}
