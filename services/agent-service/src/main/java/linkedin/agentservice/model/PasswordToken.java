package linkedin.agentservice.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "passwordToken")
public class PasswordToken {
	
	 @Transient
     public static final String SEQUENCE_NAME = "password_token_sequence";

	 @Id
	 private int id;

	 private String token;

	 private String username;

	 private boolean activated = false;

	 private Date expiryDate;

	 public Date calculateExpiryDate(int expiryTimeInMinutes) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(new Timestamp(cal.getTime().getTime()));
	    cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	    return new Date(cal.getTime().getTime());
	 }
}
