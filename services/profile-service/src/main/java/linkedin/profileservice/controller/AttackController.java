package linkedin.profileservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.profileservice.service.Implementation.AttackService;

@RestController
@RequestMapping(value = "/attack")
public class AttackController {
	
	@Autowired
    AttackService attackService;
	
	@PostMapping("/username")
    //@PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<?> validateEmail(@RequestBody String input){
        return new ResponseEntity<>(attackService.usernameValidation(input), HttpStatus.OK);
    }
}
