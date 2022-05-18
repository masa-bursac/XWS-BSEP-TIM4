package linkedin.profileservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.profileservice.config.GeneralException;
import linkedin.profileservice.dto.AuthDTO;
import linkedin.profileservice.dto.ChangePasswordDTO;
import linkedin.profileservice.dto.RegistrationDTO;
import linkedin.profileservice.dto.RegistrationRequestDTO;
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
            return new ResponseEntity(authService.login(authDTO), HttpStatus.OK);
        }catch(GeneralException e){
            return new ResponseEntity(e.getMessage(), e.getHttpStatus());
        }
    }
    
    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationDTO registrationDTO){
        try{
        	if(authService.registration(registrationDTO))
        		return new ResponseEntity(HttpStatus.OK);
        	else
        		return new ResponseEntity(HttpStatus.CONFLICT);
        }catch(GeneralException e){
            return new ResponseEntity(e.getMessage(), e.getHttpStatus());
        }
    }
    
    @GetMapping("/getUsername/{id}")
    public String getUsername(@PathVariable int id){
        return authService.getUsername(id);
    }
    
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody String username){
         authService.forgotPassword(username);
    }
    
    @PutMapping("/change-password")
    public Boolean changePassword(@RequestBody ChangePasswordDTO request){
    	 return authService.changePassword(request);
    }
    
    @GetMapping("/registration-requests")
    public List<RegistrationRequestDTO> getRegistrationRequests(){
        return authService.getRegistrationRequests();
    }
    
    @PutMapping("/approve")
    public void approveRegistrationRequest(@RequestBody int id){
        authService.approveRegistrationRequest(id);
    }

    @PutMapping("/deny")
    public void denyRegistrationRequest(@RequestBody int id){
        authService.denyRegistrationRequest(id);
    }
    
    @PutMapping("/confirm")
    public boolean confirmRegistrationRequest(@RequestBody String token){
        return authService.confirmRegistrationRequest(token);
    }
}
