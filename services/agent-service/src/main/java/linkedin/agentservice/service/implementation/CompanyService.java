package linkedin.agentservice.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import linkedin.agentservice.dto.CommentDTO;
import linkedin.agentservice.dto.CompanyDTO;
import linkedin.agentservice.dto.JobOfferCommentDTO;
import linkedin.agentservice.dto.JobOfferDTO;
import linkedin.agentservice.dto.SalaryDTO;
import linkedin.agentservice.dto.SelectionDTO;
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
	private final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	 
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
        
        logger.info("User " + company.getUsername() + " has sent registration request");

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
		logger.info("User " + company.getUsername() + " has registered company");
		
	}

	@Override
	public void denyRegistrationRequest(String companyName) {
		Company company = companyRepository.findOneByCompanyName(companyName);
		company.setCompanyStatus(CompanyStatus.DENIED);
		companyRepository.save(company);
		logger.info("User " + company.getUsername() + " has been denied for registering company");
		
	}

	@Override
	public Boolean update(UpdateCompanyDTO updateCompanyDTO) {
		Company companyForUpdating = companyRepository.findOneByUsername(updateCompanyDTO.getUsername());
		companyForUpdating.setCompanyName(updateCompanyDTO.getCompanyName());
		companyForUpdating.setPhone(updateCompanyDTO.getPhone());
		companyForUpdating.setDescription(updateCompanyDTO.getDescription());
		companyRepository.save(companyForUpdating);
		logger.info("User " + updateCompanyDTO.getUsername() + " has updated his company");
        return true;

	}

	@Override
	public Boolean addJobOffer(JobOfferDTO jobOfferDTO) {
		Company company = companyRepository.findOneByCompanyName(jobOfferDTO.getCompanyName());
		JobOffer jobOffer = new JobOffer();
		jobOffer.setId((int) sequenceGeneratorService.generateSequence(JobOffer.SEQUENCE_NAME));
		jobOffer.setJobPosition(jobOfferDTO.getJobPosition());
		jobOffer.setShare(jobOfferDTO.getShare());
		jobOffer.setComments(new ArrayList<Comment>());
		jobOffer.setSalary(new ArrayList<String>());
		jobOffer.setSelection(new ArrayList<Selection>());
		company.getJobOffers().add(jobOffer);
		
		companyRepository.save(company);
		
		logger.info("User " + company.getUsername() + " has added job offer");
		
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
		
		logger.info("User " + company.getUsername() + " has added comment");
		
		return true;
	}

	@Override
	public Boolean addSalary(SalaryDTO salaryDTO) {
		Company company = companyRepository.findOneByCompanyName(salaryDTO.getCompanyName());
		JobOffer jobOffer = new JobOffer();
		for(JobOffer jobOffers : company.getJobOffers()) {
			if(jobOffers.getId() == salaryDTO.getIdJobOffer()) {
				jobOffer = jobOffers;		
			}
		}
				
		jobOffer.getSalary().add(salaryDTO.getSalary());
		
		companyRepository.save(company);
		
		logger.info("User " + company.getUsername() + " has added job salary");
		
		return true;
	}

	@Override
	public Boolean addSelection(SelectionDTO selectionDTO) {
		Company company = companyRepository.findOneByCompanyName(selectionDTO.getCompanyName());
		JobOffer jobOffer = new JobOffer();
		for(JobOffer jobOffers : company.getJobOffers()) {
			if(jobOffers.getId() == selectionDTO.getIdJobOffer()) {
				jobOffer = jobOffers;		
			}
		}
		
		Selection selection = new Selection();
		selection.setId((int) sequenceGeneratorService.generateSequence(Selection.SEQUENCE_NAME));
		selection.setComment(selectionDTO.getComment());
		selection.setDuration(selectionDTO.getDuration());
		selection.setMark(selectionDTO.getMark());
		
		jobOffer.getSelection().add(selection);
		
		companyRepository.save(company);
		
		logger.info("User " + company.getUsername() + " has added job selection");
		
		return true;
	}

	@Override
	public UpdateCompanyDTO getCompany(String username) {
		UpdateCompanyDTO updateCompanyDTO = new UpdateCompanyDTO();
		Company company = companyRepository.findOneByUsername(username);
		updateCompanyDTO.setCompanyName(company.getCompanyName());
		updateCompanyDTO.setPhone(company.getPhone());
		updateCompanyDTO.setDescription(company.getDescription());
		
		return updateCompanyDTO;
		
	}

	@Override
	public List<JobOfferCommentDTO> getAllJobOffers() {
		List<Company> companies = companyRepository.findAll();
		List<JobOfferCommentDTO> jobOffers = new ArrayList<JobOfferCommentDTO>();
		
		for(Company company: companies) {
			for(JobOffer jobOffer: company.getJobOffers()) {
				JobOfferCommentDTO jobOfferDTO = new JobOfferCommentDTO();
				jobOfferDTO.setCompanyName(company.getCompanyName());
				jobOfferDTO.setJobPosition(jobOffer.getJobPosition());
				jobOfferDTO.setId(jobOffer.getId());
				jobOffers.add(jobOfferDTO);
			}			
		}
		
		return jobOffers;
	}

	@Override
	public List<JobOfferCommentDTO> getAllSharedJobOffers() {
		List<Company> companies = companyRepository.findAll();
		List<JobOfferCommentDTO> jobOffers = new ArrayList<JobOfferCommentDTO>();
		
		for(Company company: companies) {
			for(JobOffer jobOffer: company.getJobOffers()) {
				if(jobOffer.getShare()) {
					JobOfferCommentDTO jobOfferDTO = new JobOfferCommentDTO();
					jobOfferDTO.setCompanyName(company.getCompanyName());
					jobOfferDTO.setJobPosition(jobOffer.getJobPosition());
					jobOfferDTO.setId(jobOffer.getId());
					jobOffers.add(jobOfferDTO);
				}				
			}			
		}
		
		return jobOffers;
	}

	@Override
	public List<JobOfferCommentDTO> searchJobOffers(String jobPosition) {
		List<Company> companies = companyRepository.findAll();
		List<JobOfferCommentDTO> jobOffers = new ArrayList<JobOfferCommentDTO>();
		
		for(int i=0; i<companies.size();i++) {	

			for(int j=0; j<companies.get(i).getJobOffers().size();j++) {

				if(companies.get(i).getJobOffers().get(j).getJobPosition().toLowerCase().contains(jobPosition.toLowerCase()) && 
						companies.get(i).getJobOffers().get(j).getShare() == true) {
					JobOfferCommentDTO jobOfferCommentDTO = new JobOfferCommentDTO();
					jobOfferCommentDTO.setCompanyName(companies.get(i).getCompanyName());
					jobOfferCommentDTO.setId(companies.get(i).getJobOffers().get(j).getId());
					jobOfferCommentDTO.setJobPosition(companies.get(i).getJobOffers().get(j).getJobPosition());
					jobOffers.add(jobOfferCommentDTO);
				}
			}
		}
		
		return jobOffers;
	}

}
