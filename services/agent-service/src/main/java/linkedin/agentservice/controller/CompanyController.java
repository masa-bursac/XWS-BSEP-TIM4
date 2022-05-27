package linkedin.agentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.dto.CompanyDTO;
import linkedin.agentservice.dto.RegistrationDTO;
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

}
