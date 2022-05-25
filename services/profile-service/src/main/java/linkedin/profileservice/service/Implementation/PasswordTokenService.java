package linkedin.profileservice.service.Implementation;

import java.util.UUID;

import org.springframework.stereotype.Service;

import linkedin.profileservice.model.PasswordToken;
import linkedin.profileservice.model.Profile;
import linkedin.profileservice.repository.PasswordTokenRepository;

@Service
public class PasswordTokenService {

	 private final PasswordTokenRepository passwordTokenRepository;
	 static SequenceGeneratorService sequenceGeneratorService;

	 public PasswordTokenService(PasswordTokenRepository passwordTokenRepository, SequenceGeneratorService sequenceGeneratorService) {
		 this.passwordTokenRepository = passwordTokenRepository;
		 this.sequenceGeneratorService = sequenceGeneratorService;
	 }

	 public PasswordToken createToken(String username){
	     PasswordToken passwordToken = new PasswordToken();
	     passwordToken.setId((int) sequenceGeneratorService.generateSequence(PasswordToken.SEQUENCE_NAME));
	     passwordToken.setUsername(username);
	     passwordToken.setExpiryDate(passwordToken.calculateExpiryDate(24*60));
	     passwordToken.setToken(UUID.randomUUID().toString());
	     return passwordTokenRepository.save(passwordToken);
	 }
}
