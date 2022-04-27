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

	static ProfileRepository pr;
	static AuthRepository ar;
	static SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	public ProfileServiceApplication(ProfileRepository pr, AuthRepository ar, SequenceGeneratorService sg) {
		this.pr = pr;
		this.ar = ar;
		this.sequenceGeneratorService = sg;
	}
	public static void main(String[] args) {
		SpringApplication.run(ProfileServiceApplication.class, args);
		Profile profile = new Profile();
		profile.setBiography("happy");
		profile.setIsPrivate(true);
		UserInfo us= new UserInfo();
		us.setUsername("milan");
		us.setPassword("1234");
        us.setId((int) sequenceGeneratorService.generateSequence(UserInfo.SEQUENCE_NAME));

		ar.save(us);
        profile.setId((int) sequenceGeneratorService.generateSequence(Profile.SEQUENCE_NAME));
		System.out.println(profile);
		pr.save(profile);
		
	}

}
