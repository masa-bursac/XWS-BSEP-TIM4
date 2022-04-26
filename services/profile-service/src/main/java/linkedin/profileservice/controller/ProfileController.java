package linkedin.profileservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.profileservice.service.IProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final IProfileService profileService;

    public ProfileController(IProfileService profileService){this.profileService = profileService;}
}
