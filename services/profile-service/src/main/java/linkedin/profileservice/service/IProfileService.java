package linkedin.profileservice.service;

import org.springframework.util.MultiValueMap;

import linkedin.profileservice.dto.UpdateDTO;

public interface IProfileService {

	Boolean update(UpdateDTO userInfo);

}
