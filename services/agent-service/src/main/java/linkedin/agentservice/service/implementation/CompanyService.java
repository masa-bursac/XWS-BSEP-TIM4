package linkedin.agentservice.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.agentservice.dto.CommentDTO;
import linkedin.agentservice.dto.CompanyDTO;
import linkedin.agentservice.dto.JobOfferDTO;
import linkedin.agentservice.dto.UpdateCompanyDTO;
import linkedin.agentservice.model.Comment;
import linkedin.agentservice.model.Company;
import linkedin.agentservice.model.CompanyStatus;
import linkedin.agentservice.model.JobOffer;
import linkedin.agentservice.model.Roles;
import linkedin.agentservice.model.Selection;
import linkedin.agentservice.model.UserInfo;
import linkedin.agentservice.repository.AgentRepository;
import linkedin.agentservice.repository.CompanyRepository;
import linkedin.agentservice.service.ICompanyService;

@Service
public class CompanyService implements ICompanyService {
	
	private final CompanyRepository companyRepository;
	static SequenceGeneratorService sequenceGeneratorService;
	private final EmailService emailService;
	private final AgentRepository agentRepository;
	 
	@Autowired
	public CompanyService(CompanyRepository companyRepository, SequenceGeneratorService sequenceGeneratorService, EmailService emailService,
			AgentRepository agentRepository) {
		this.companyRepository = companyRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
		this.emailService = emailService;
		this.agentRepository = agentRepository;
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
		UserInfo user = agentRepository.findOneByUsername(company.getUsername());
		user.setRole(Roles.OWNER);
		agentRepository.save(user);
		companyRepository.save(company);
		
	}

	@Override
	public void denyRegistrationRequest(String companyName) {
		Company company = companyRepository.findOneByCompanyName(companyName);
		company.setCompanyStatus(CompanyStatus.DENIED);
		companyRepository.save(company);
		
	}

	@Override
	public Boolean update(UpdateCompanyDTO updateCompanyDTO) {
		Company companyForUpdating = companyRepository.findOneByCompanyName(updateCompanyDTO.getCompanyName());
		companyForUpdating.setDescription(updateCompanyDTO.getDescription());
		companyRepository.save(companyForUpdating);
        return true;

	}

	@Override
	public Boolean addJobOffer(JobOfferDTO jobOfferDTO) {
		Company company = companyRepository.findOneByCompanyName(jobOfferDTO.getCompanyName());
		JobOffer jobOffer = new JobOffer();
		jobOffer.setId((int) sequenceGeneratorService.generateSequence(JobOffer.SEQUENCE_NAME));
		jobOffer.setJobPosition(jobOfferDTO.getJobPosition());
		jobOffer.setComments(new ArrayList<Comment>());
		jobOffer.setMarks(new ArrayList<Integer>());
		jobOffer.setSalary(new ArrayList<String>());
		jobOffer.setSelection(new ArrayList<Selection>());
		company.getJobOffers().add(jobOffer);
		
		companyRepository.save(company);
		return true;
	}

	@Override
	public Boolean addComment(CommentDTO commentDTO) {
		Company company = companyRepository.findOneByCompanyName(commentDTO.getCompanyName());
		JobOffer jobOffer = new JobOffer();
		for(JobOffer jobOffers : company.getJobOffers()) {
			if(jobOffers.getId() == commentDTO.getIdJobOffer()) {
				jobOffer = jobOffers;		
			}
		}
		
		Comment comment = new Comment();
		comment.setId((int) sequenceGeneratorService.generateSequence(Comment.SEQUENCE_NAME));
		comment.setContent(commentDTO.getContent());
		comment.setUserId(commentDTO.getUserId());
		
		jobOffer.getComments().add(comment);
		
		companyRepository.save(company);
		
		return true;
	}

}
