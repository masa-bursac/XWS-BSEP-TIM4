package linkedin.profileservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.profileservice.service.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }
}
