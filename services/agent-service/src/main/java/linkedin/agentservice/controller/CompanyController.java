package linkedin.agentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.agentservice.config.GeneralException;
import linkedin.agentservice.dto.CommentDTO;
import linkedin.agentservice.dto.CompanyDTO;
import linkedin.agentservice.dto.JobOfferCommentDTO;
import linkedin.agentservice.dto.JobOfferDTO;
import linkedin.agentservice.dto.SalaryDTO;
import linkedin.agentservice.dto.SelectionDTO;
import linkedin.agentservice.dto.UpdateCompanyDTO;
import linkedin.agentservice.service.ICompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }
	
    @PreAuthorize("hasRole('USER')")
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
	
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/registration-requests")
    public List<CompanyDTO> getRegistrationRequests(){
        return companyService.getRegistrationRequests();
    }
	
    @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/approve")
    public void approveRegistrationRequest(@RequestBody String companyName){
		companyService.approveRegistrationRequest(companyName);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deny")
    public void denyRegistrationRequest(@RequestBody String companyName){
    	companyService.denyRegistrationRequest(companyName);
    }
    
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/updateCompany")
    public ResponseEntity update(@RequestBody UpdateCompanyDTO updateCompanyDTO) {
        try {
        	if(companyService.update(updateCompanyDTO))
        		return new ResponseEntity(companyService.update(updateCompanyDTO), HttpStatus.OK);
        	else
        		return new ResponseEntity(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/addJobOffer")
    public ResponseEntity addJobOffer(@RequestBody JobOfferDTO jobOfferDTO) {
        try {
        	return new ResponseEntity(companyService.addJobOffer(jobOfferDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addComment")
    public ResponseEntity addComment(@RequestBody CommentDTO commentDTO) {
        try {
        	return new ResponseEntity(companyService.addComment(commentDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addSalary")
    public ResponseEntity addSalary(@RequestBody SalaryDTO salaryDTO) {
        try {
        	return new ResponseEntity(companyService.addSalary(salaryDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addSelection")
    public ResponseEntity addSelection(@RequestBody SelectionDTO selectionDTO) {
        try {
        	return new ResponseEntity(companyService.addSelection(selectionDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/getCompany/{username}")
    public UpdateCompanyDTO getCompany(@PathVariable String username){
        return companyService.getCompany(username);
    }
    
    @GetMapping("/getAllJobOffers")
    public List<JobOfferCommentDTO> getAllJobOffers(){
        return companyService.getAllJobOffers();
    }
    
    @GetMapping("/getAllSharedJobOffers")
    public List<JobOfferCommentDTO> getAllSharedJobOffers(){
        return companyService.getAllSharedJobOffers();
    }
    
    @GetMapping("/searchJobOffers/{jobPosition}")
    public ResponseEntity searchJobOffers(@PathVariable String jobPosition) {
        try {
        	return new ResponseEntity(companyService.searchJobOffers(jobPosition), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
