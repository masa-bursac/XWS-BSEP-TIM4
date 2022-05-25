package linkedin.profileservice.model;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "permissions")
public class Permission implements GrantedAuthority{

	@Transient
    public static final String SEQUENCE_NAME = "permission_sequence";
	
	@Id
    private Long id;

    @Field
    private String name;

	@Override
	public String getAuthority() {
		return name;
	}

}
