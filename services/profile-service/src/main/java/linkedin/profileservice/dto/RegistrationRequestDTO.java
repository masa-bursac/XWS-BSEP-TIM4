package linkedin.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequestDTO {

	private int id;
	private String name;
	private String surname;
	private String username;
	private String email;
}
