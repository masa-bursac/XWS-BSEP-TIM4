package linkedin.agentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.agentservice.service.implementation.AttackService;


@RestController
@RequestMapping(value = "/attack")
public class AttackController {

	@Autowired
    AttackService attackService;
	
	@PostMapping("/username")
    public ResponseEntity<?> validateUsername(@RequestBody String input){
        return new ResponseEntity<>(attackService.usernameValidation(input), HttpStatus.OK);
    }
	
	@PostMapping("/name")
    public ResponseEntity<?> validateName(@RequestBody String input){
        return new ResponseEntity<>(attackService.nameValidation(input), HttpStatus.OK);
    }
	
	@PostMapping("/email")
    public ResponseEntity<?> validateEmail(@RequestBody String input){
        return new ResponseEntity<>(attackService.emailValidation(input), HttpStatus.OK);
    }
	
	@PostMapping("/password")
    public ResponseEntity<?> validatePassword(@RequestBody String input){
        return new ResponseEntity<>(attackService.passwordValidation(input), HttpStatus.OK) ;
    }
	
	@PostMapping("/phoneNumber")
    public ResponseEntity<?> validatePhoneNumber(@RequestBody String input){
        return new ResponseEntity<>(attackService.phoneNumberValidation(input), HttpStatus.OK) ;
    }
	
	@PostMapping("/escape")
    public ResponseEntity<?> charachterEscaping(@RequestBody String input){
        return new ResponseEntity<>(attackService.escaping(input), HttpStatus.OK);
    }
	
	@PostMapping("/date")
	public ResponseEntity<?> validateDate(@RequestBody String input){
        return new ResponseEntity<>(attackService.dateValidation(input), HttpStatus.OK);
    }
	
	@PostMapping("/companyName")
	public ResponseEntity<?> validateCompanyName(@RequestBody String input){
        return new ResponseEntity<>(attackService.companyNameValidation(input), HttpStatus.OK);
    }
	
	@PostMapping("/description")
	public ResponseEntity<?> validateDescription(@RequestBody String input){
        return new ResponseEntity<>(attackService.descriptionValidation(input), HttpStatus.OK);
    }
	
	@PostMapping("/mark")
	public ResponseEntity<?> validateMark(@RequestBody String input){
        return new ResponseEntity<>(attackService.markValidation(input), HttpStatus.OK);
    }
}
