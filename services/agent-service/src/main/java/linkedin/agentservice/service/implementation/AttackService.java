package linkedin.agentservice.service.implementation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import linkedin.agentservice.dto.RegistrationDTO;
import linkedin.agentservice.dto.ResponseDTO;

@Service
public class AttackService {
	
	private final Logger logger = LoggerFactory.getLogger(AttackService.class);

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
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        return responseDTO;
	}

	public ResponseDTO nameValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^[a-zA-Z_ \']+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
    }

	public ResponseDTO emailValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
    }

	public ResponseDTO passwordValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
    }

	public ResponseDTO phoneNumberValidation(String input) {
		ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
	}
	
    public ResponseDTO escaping(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }
        final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%"};

        for (int i = 0 ; i < metaCharacters.length ; i++){
            if(input.contains(metaCharacters[i])){
                input = input.replace(metaCharacters[i],'\\' + metaCharacters[i]);
            }
        }
        responseDTO.setUsername(input);
        return responseDTO;
    }
    
    public ResponseDTO dateValidation(String input)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        
        Pattern p = Pattern.compile(regex);
        if(input.contains("\"")){
        	input = input.split("\"")[1];
        }
        Matcher m = p.matcher(input.split("T")[0]);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
    }

    public Boolean validateRegistration(RegistrationDTO registrationDTO) {
    	if(usernameValidation(registrationDTO.getUsername()).isBool() &&
    			nameValidation(registrationDTO.getName()).isBool() &&
    			nameValidation(registrationDTO.getSurname()).isBool() &&
    			emailValidation(registrationDTO.getEmail()).isBool() &&
    			passwordValidation(registrationDTO.getPassword()).isBool() &&
    			phoneNumberValidation(registrationDTO.getPhone()).isBool() &&
    			dateValidation(registrationDTO.getDateOfBirth()).isBool()){
    		return true;
    	}
    	else
    		return false;
    }

	public ResponseDTO companyNameValidation(String input) {
		ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "(?:\\s*[a-zA-Z0-9,_\\.\\*\\+\\&\\#\\~\\-\\!\\@\\;]{2,}\\s*)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
	}

	public ResponseDTO descriptionValidation(String input) {
		ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^[ A-Za-z0-9_@./!#&+-]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
	}

	public ResponseDTO markValidation(String input) {
		ResponseDTO responseDTO = new ResponseDTO();
        if (input == null) {
            responseDTO.setBool(false);
            return responseDTO;
        }

        String regex = "^[1-5]$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        responseDTO.setBool(m.matches());
        
        if(!m.matches()) {
        	logger.debug("Format not adequate, status:400 Bad Request");
        	logger.warn("Injection attempt!");
        }
        
        return responseDTO;
	}
}
