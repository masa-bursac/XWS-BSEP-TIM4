package linkedin.profileservice.service.Implementation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import linkedin.profileservice.dto.ResponseDTO;

@Service
public class AttackService {

	public ResponseDTO usernameValidation(String input) {
		
		ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }
        String regex = "^[a-zA-Z0-9]([.-](?![.-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        return responseDTO;
	}

}
