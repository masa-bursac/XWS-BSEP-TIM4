package linkedin.agentservice.dto;

public class JobOfferDTO {
	
	private String companyName;

	private String jobPosition;
		

	public JobOfferDTO() {
		super();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	
	
}
