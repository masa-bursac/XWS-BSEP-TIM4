package linkedin.agentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.dto.CompanyDTO;
import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.dto.RegistrationRequestDTO;
import linkedin.agentservice.service.IAgentService;
import linkedin.agentservice.service.ICompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }
	
	@PostMapping("/registration")
    public ResponseEntity registrationCompany(@RequestBody CompanyDTO companyDTO){
    	
        try{
        	if(companyService.registrationCompany(companyDTO))
        		return new ResponseEntity(HttpStatus.OK);
        	else
        		return new ResponseEntity(HttpStatus.CONFLICT);
        }catch(GeneralException e){
            return new ResponseEntity(e.getMessage(), e.getHttpStatus());
        }
    }
	
	@GetMapping("/registration-requests")
    public List<CompanyDTO> getRegistrationRequests(){
        return companyService.getRegistrationRequests();
    }
	
	@PutMapping("/approve")
    public void approveRegistrationRequest(@RequestBody String companyName){
		companyService.approveRegistrationRequest(companyName);
    }

    @PutMapping("/deny")
    public void denyRegistrationRequest(@RequestBody String companyName){
    	companyService.denyRegistrationRequest(companyName);
    }

}
