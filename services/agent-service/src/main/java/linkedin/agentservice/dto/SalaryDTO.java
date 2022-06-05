package linkedin.agentservice.dto;

public class SalaryDTO {

	private String salary;
	
	private int userId;
	
	private String companyName;
	
	private int idJobOffer;

	
	
	public SalaryDTO() {
		super();
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getIdJobOffer() {
		return idJobOffer;
	}

	public void setIdJobOffer(int idJobOffer) {
		this.idJobOffer = idJobOffer;
	}
	
	
}
