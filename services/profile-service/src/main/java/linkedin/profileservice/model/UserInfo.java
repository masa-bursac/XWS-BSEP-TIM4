package linkedin.profileservice.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import linkedin.profileservice.dto.RegistrationDTO;


@Document(collection = "userinfo")
public class UserInfo implements UserDetails{
	
	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
    private int id;
	
    @Field
    private String username;
    
    @Field
    private String password;
    
    @Field
    private String name;
    
    @Field
    private String surname;
    
    @Field
    private String email;
    
    @Field
    private String phone;
    
    @Field
    private Gender gender;
    
    @Field
    private LocalDate dateOfBirth;
    
    @Field
    private Roles role;
    
    @Field
    private AccountStatus accountStatus;
    
    @Field
    private int loginCounter;
    
    @Field 
    private Date blockDate;

	public UserInfo() {
		super();
	}
	

	public UserInfo(RegistrationDTO registrationDTO) {
		// TODO Auto-generated constructor stub
		this.username = registrationDTO.getUsername();
		this.password = registrationDTO.getPassword();
		this.name = registrationDTO.getName();
		this.surname = registrationDTO.getSurname();
		this.email = registrationDTO.getEmail();
        this.phone = registrationDTO.getPhone();
        String[] array = registrationDTO.getDateOfBirth().split("T");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(array[0],formatter);
        this.dateOfBirth = dateTime;
        if(registrationDTO.getGender().toLowerCase().equals(Gender.Male.toString().toLowerCase(Locale.ROOT)))
            this.gender = Gender.Male;
        else if(registrationDTO.getGender().toLowerCase().equals(Gender.Female.toString().toLowerCase(Locale.ROOT)))
            this.gender = Gender.Female;
        else
            this.gender = Gender.NonBinary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getLoginCounter() {
		return loginCounter;
	}

	public void setLoginCounter(int loginCounter) {
		this.loginCounter = loginCounter;
	}

	public Date getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));

        return list;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
