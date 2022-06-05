package linkedin.agentservice.dto;

public class JobOfferDTO {
	
	private String companyName;

	private String jobPosition;
	
	private Boolean share;
		

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

	public Boolean getShare() {
		return share;
	}

	public void setShare(Boolean share) {
		this.share = share;
	}
	
	
	
}
