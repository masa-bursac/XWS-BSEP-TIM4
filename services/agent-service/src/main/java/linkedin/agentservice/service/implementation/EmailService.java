package linkedin.agentservice.service.implementation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import linkedin.agentservice.config.EmailContext;
import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.model.PasswordToken;
import linkedin.agentservice.model.Token;
import linkedin.agentservice.model.UserInfo;
import linkedin.agentservice.repository.AgentRepository;
import linkedin.agentservice.repository.PasswordTokenRepository;
import linkedin.agentservice.service.IEmailService;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements IEmailService {
	
	private final AgentRepository agentRepository;
	private final PasswordTokenRepository passwordTokenRepository;
	private final PasswordTokenService passwordTokenService;
	private final EmailContext emailContext;
	private final Token token;
	
	@Autowired
    public EmailService(AgentRepository agentRepository, PasswordTokenRepository passwordTokenRepository, PasswordTokenService passwordTokenService,
    		EmailContext emailContext, Token token) {
        this.agentRepository = agentRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordTokenService = passwordTokenService;
        this.emailContext = emailContext;
        this.token = token;
    }
	

	public void approveRegistrationMail(UserInfo savedUser) {

        String title = "Your registration has been approved.";
        PasswordToken passwordToken = passwordTokenRepository.findOneByUsername(savedUser.getUsername());

        Context context = new Context();
        context.setVariable("name", String.format("%s %s", savedUser.getName(), savedUser.getSurname()));
        context.setVariable("link", String.format("http://localhost:4200/login/%s", passwordToken.getToken()));
        emailContext.send("firma4validation@gmail.com", title, "approveRegistration", context);
		
	}


	public void denyRegistrationMail(UserInfo savedUser) {

        String title = "Your registration has been denied.";
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", savedUser.getName(), savedUser.getSurname()));
        emailContext.send("firma4validation@gmail.com", title, "denyRegistration", context);		
	}


}
