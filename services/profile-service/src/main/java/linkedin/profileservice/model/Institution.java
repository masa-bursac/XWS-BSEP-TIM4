package linkedin.profileservice.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import linkedin.profileservice.dto.InstitutionDTO;
import linkedin.profileservice.dto.InstitutionUpdateDTO;
import linkedin.profileservice.dto.RegistrationDTO;


@Document(collection = "institution")
public class Institution {
	
	@Transient
    public static final String SEQUENCE_NAME = "institution_sequence";
	
	@Id
    private int id;
	
	@Field
	private String name;
	
	@Field
	private String position;
	
	@Field
	private LocalDate start;

	@Field
	private LocalDate end;
	
	@Field
	private int userInfoId;

	public Institution() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public LocalDate getStart() {
		return start;
	}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	public LocalDate getEnd() {
		return end;
	}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	public int getUserInfoId() {
		return userInfoId;
	}
	public void setUserInfoId(int userInfoId) {
		this.userInfoId = userInfoId;
	}
	
	public Institution(InstitutionDTO institutionDTO) {
		this.name= institutionDTO.getName();
		this.position = institutionDTO.getPosition();
		this.userInfoId = institutionDTO.getUserInfoId();
     
        String[] arrayStart = institutionDTO.getStart().split("T");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDateTime = LocalDate.parse(arrayStart[0],formatter);
        this.start = startDateTime;
        
        String[] arrayEnd = institutionDTO.getEnd().split("T");
        LocalDate endDateTime = LocalDate.parse(arrayEnd[0],formatter);
        this.end = endDateTime;
	}

	public Institution(InstitutionUpdateDTO institutionDTO) {
		this.id = institutionDTO.getId();
		this.name= institutionDTO.getName();
		this.position = institutionDTO.getPosition();
		this.userInfoId = institutionDTO.getUserInfoId();
     
        String[] arrayStart = institutionDTO.getStart().split("T");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDateTime = LocalDate.parse(arrayStart[0],formatter);
        this.start = startDateTime;
        
        String[] arrayEnd = institutionDTO.getEnd().split("T");
        LocalDate endDateTime = LocalDate.parse(arrayEnd[0],formatter);
        this.end = endDateTime;
	}
}
