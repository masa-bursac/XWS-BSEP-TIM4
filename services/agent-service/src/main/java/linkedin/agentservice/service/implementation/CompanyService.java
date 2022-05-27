package linkedin.agentservice.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.agentservice.dto.CompanyDTO;
import linkedin.agentservice.dto.RegistrationRequestDTO;
import linkedin.agentservice.model.AccountStatus;
import linkedin.agentservice.model.Company;
import linkedin.agentservice.model.CompanyStatus;
import linkedin.agentservice.model.JobOffer;
import linkedin.agentservice.model.UserInfo;
import linkedin.agentservice.repository.CompanyRepository;
import linkedin.agentservice.service.ICompanyService;

@Service
public class CompanyService implements ICompanyService {
	
	private final CompanyRepository companyRepository;
	static SequenceGeneratorService sequenceGeneratorService;
	private final EmailService emailService;
	 
	@Autowired
	public CompanyService(CompanyRepository companyRepository, SequenceGeneratorService sequenceGeneratorService, EmailService emailService) {
		this.companyRepository = companyRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.emailService = emailService;
	}

	@Override
	public Boolean registrationCompany(CompanyDTO companyDTO) {
		
        Company company = new Company();
        company.setId((int) sequenceGeneratorService.generateSequence(Company.SEQUENCE_NAME));
        company.setCompanyName(companyDTO.getCompanyName());
        company.setPhone(companyDTO.getPhone());
        company.setDescription(companyDTO.getDescription());
        company.setCompanyStatus(CompanyStatus.PENDING);
        company.setUsername(companyDTO.getUsername());
        company.setJobOffers(new ArrayList<JobOffer>());

        companyRepository.save(company);

        return true;
	}

	@Override
	public List<CompanyDTO> getRegistrationRequests() {
		List<Company> companies = companyRepository.findAllByCompanyStatus(CompanyStatus.PENDING);
        List<CompanyDTO> companyResponses = new ArrayList<>();
        for (Company company: companies) {
        	CompanyDTO companyRequestDTO = new CompanyDTO();
        	companyRequestDTO.setCompanyName(company.getCompanyName());
        	companyRequestDTO.setPhone(company.getPhone());
        	companyRequestDTO.setDescription(company.getDescription());
        	companyRequestDTO.setUsername(company.getUsername());
        	companyResponses.add(companyRequestDTO);
        }
        return companyResponses;
	}

	@Override
	public void approveRegistrationRequest(String companyName) {
		Company company = companyRepository.findOneByCompanyName(companyName);
		company.setCompanyStatus(CompanyStatus.APPROVED);
		companyRepository.save(company);
		
	}

	@Override
	public void denyRegistrationRequest(String companyName) {
		Company company = companyRepository.findOneByCompanyName(companyName);
		company.setCompanyStatus(CompanyStatus.DENIED);
		companyRepository.save(company);
		
	}

}
