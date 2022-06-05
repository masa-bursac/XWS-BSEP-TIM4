package linkedin.agentservice.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "company")
public class Company {

	@Transient
    public static final String SEQUENCE_NAME = "company_sequence";
	
	@Id
    private int id;
	
	@Field
    private String companyName;
	
    @Field
    private String phone;
      
    @Field
    private String description;   
    
    @Field
    private CompanyStatus companyStatus;
    
    @Field
    private String username;
    
    @Field
    private List<JobOffer> jobOffers;

	public Company() {
		super();
	}

	public Company(int id, String companyName, String phone, String description) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.phone = phone;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<JobOffer> getJobOffers() {
		return jobOffers;
	}

	public void setJobOffers(List<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public CompanyStatus getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(CompanyStatus companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
    
}
