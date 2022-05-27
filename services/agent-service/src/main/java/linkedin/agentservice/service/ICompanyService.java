package linkedin.agentservice.service;

import java.util.List;

import linkedin.agentservice.dto.CompanyDTO;

public interface ICompanyService {

	Boolean registrationCompany(CompanyDTO companyDTO);

	List<CompanyDTO> getRegistrationRequests();

	void approveRegistrationRequest(String companyName);

	void denyRegistrationRequest(String companyName);

}
