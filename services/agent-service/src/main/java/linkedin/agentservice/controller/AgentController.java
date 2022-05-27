package linkedin.agentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.dto.AuthDTO;
import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.dto.RegistrationRequestDTO;
import linkedin.agentservice.service.IAgentService;


@RestController
@RequestMapping("/agent")
public class AgentController {
	
	private final IAgentService agentService;

    public AgentController(IAgentService agentService) {
        this.agentService = agentService;
    }
    
    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationDTO registrationDTO){
    	
        try{
        	if(agentService.registration(registrationDTO))
        		return new ResponseEntity(HttpStatus.OK);
        	else
        		return new ResponseEntity(HttpStatus.CONFLICT);
        }catch(GeneralException e){
            return new ResponseEntity(e.getMessage(), e.getHttpStatus());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDTO authDTO){
        try{
            return new ResponseEntity(agentService.login(authDTO), HttpStatus.OK);
        }catch(GeneralException e){
            return new ResponseEntity(e.getMessage(), e.getHttpStatus());
        }
    }
    
    @GetMapping("/registration-requests")
    public List<RegistrationRequestDTO> getRegistrationRequests(){
        return agentService.getRegistrationRequests();
    }
    
    @PutMapping("/approve")
    public void approveRegistrationRequest(@RequestBody int id){
        agentService.approveRegistrationRequest(id);
    }

    @PutMapping("/deny")
    public void denyRegistrationRequest(@RequestBody int id){
        agentService.denyRegistrationRequest(id);
    }

}
