package linkedin.profileservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.RegistrationDTO;
import linkedin.profileservice.model.UserInfo;
import linkedin.profileservice.service.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDTO authDTO){
        try{
        	System.out.println(authDTO.getPassword());
            return new ResponseEntity(authService.login(authDTO), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationDTO registrationDTO){
        try{
        	if(authService.registration(registrationDTO))
        		return new ResponseEntity(authService.registration(registrationDTO), HttpStatus.OK);
        	else
        		return new ResponseEntity(HttpStatus.CONFLICT);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
