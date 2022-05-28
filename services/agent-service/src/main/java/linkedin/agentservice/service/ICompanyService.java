package linkedin.agentservice.service;

import java.util.List;

import org.springframework.util.MultiValueMap;

import linkedin.agentservice.dto.CommentDTO;
import linkedin.agentservice.dto.CompanyDTO;
import linkedin.agentservice.dto.JobOfferDTO;
import linkedin.agentservice.dto.SalaryDTO;
import linkedin.agentservice.dto.SelectionDTO;
import linkedin.agentservice.dto.UpdateCompanyDTO;

public interface ICompanyService {

	Boolean registrationCompany(CompanyDTO companyDTO);

	List<CompanyDTO> getRegistrationRequests();

	void approveRegistrationRequest(String companyName);

	void denyRegistrationRequest(String companyName);

	Boolean update(UpdateCompanyDTO updateCompanyDTO);

	Boolean addJobOffer(JobOfferDTO jobOfferDTO);

	Boolean addComment(CommentDTO commentDTO);

	Boolean addSalary(SalaryDTO salaryDTO);

	Boolean addSelection(SelectionDTO selectionDTO);

}
