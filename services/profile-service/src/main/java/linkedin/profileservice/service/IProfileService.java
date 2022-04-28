package linkedin.profileservice.service;

import linkedin.profileservice.dto.InstitutionDTO;
import linkedin.profileservice.dto.UpdateDTO;

public interface IProfileService {

	Boolean update(UpdateDTO userInfo);
	Boolean addExperience(InstitutionDTO institutionDTO);

}
