package linkedin.profileservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import linkedin.profileservice.model.Profile;
import linkedin.profileservice.model.UserInfo;
import linkedin.profileservice.repository.AuthRepository;
import linkedin.profileservice.repository.InstitutionRepository;
import linkedin.profileservice.repository.ProfileRepository;
import linkedin.profileservice.service.Implementation.ProfileService;
import linkedin.profileservice.service.Implementation.SequenceGeneratorService;

@SpringBootApplication
public class ProfileServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
		
	}

}
