package linkedin.profileservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.profileservice.dto.InstitutionDTO;
import linkedin.profileservice.dto.InstitutionUpdateDTO;
import linkedin.profileservice.dto.SkillDTO;
import linkedin.profileservice.dto.UpdateDTO;
import linkedin.profileservice.model.Institution;
import linkedin.profileservice.model.Skill;
import linkedin.profileservice.service.IProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final IProfileService profileService;

    public ProfileController(IProfileService profileService) {
    	this.profileService = profileService;
    }
    
    @PutMapping("/update")
    public ResponseEntity edit(@RequestBody UpdateDTO userInfo) {
        try {
        	if(profileService.update(userInfo))
        		return new ResponseEntity(profileService.update(userInfo), HttpStatus.OK);
        	else
        		return new ResponseEntity(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/addExperience")
    public ResponseEntity addExperience(@RequestBody InstitutionDTO institutionDTO) {
        try {
        	return new ResponseEntity(profileService.addExperience(institutionDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/addEducation")
    public ResponseEntity addEducation(@RequestBody InstitutionDTO institutionDTO) {
        try {
        	return new ResponseEntity(profileService.addEducation(institutionDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/addSkill")
    public ResponseEntity addSkill(@RequestBody SkillDTO skillDTO) {
        try {
        	return new ResponseEntity(profileService.addSkill(skillDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/addInterest")
    public ResponseEntity addInterest(@RequestBody SkillDTO skillDTO) {
        try {
        	return new ResponseEntity(profileService.addInterest(skillDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateExperience")
    public ResponseEntity updateExperience(@RequestBody InstitutionUpdateDTO institutionDTO) {
        try {
        	return new ResponseEntity(profileService.updateExperience(institutionDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateEducation")
    public ResponseEntity updateEducation(@RequestBody InstitutionUpdateDTO institutionDTO) {
        try {
        	return new ResponseEntity(profileService.updateEducation(institutionDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateSkill")
    public ResponseEntity updateSkill(@RequestBody Skill skill) {
        try {
        	return new ResponseEntity(profileService.updateSkill(skill), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/updateInterest")
    public ResponseEntity updateInterest(@RequestBody Skill skill) {
        try {
        	return new ResponseEntity(profileService.updateInterest(skill), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteExperience")
    public ResponseEntity deleteExperience(@RequestBody InstitutionUpdateDTO institutionDTO) {
        try {
        	return new ResponseEntity(profileService.deleteExperience(institutionDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteEducation")
    public ResponseEntity deleteEducation(@RequestBody InstitutionUpdateDTO institutionDTO) {
        try {
        	return new ResponseEntity(profileService.deleteEducation(institutionDTO), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteSkill")
    public ResponseEntity deleteSkill(@RequestBody Skill skill) {
        try {
        	return new ResponseEntity(profileService.deleteSkill(skill), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteInterest")
    public ResponseEntity deleteInterest(@RequestBody Skill skill) {
        try {
        	return new ResponseEntity(profileService.deleteInterest(skill), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getPublicProfiles")
    public ResponseEntity getPublicProfiles() {
        try {
        	return new ResponseEntity(profileService.getPublicProfiles(), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getByUsername/{username}")
    public ResponseEntity getByUsername(@PathVariable String username) {
        try {
        	return new ResponseEntity(profileService.getByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/addPost/{postId}/{userInfoId}")
    public Boolean addPost(@PathVariable int postId, @PathVariable int userInfoId){
        return profileService.addPost(postId,userInfoId);
    }
    
	@GetMapping("/getAllPublicIds")
	public List<Integer> getAllPublicIds() {
        return profileService.getAllPublicIds();
    }
	
	@PutMapping("/follow/{loggedInId}/{currentId}")
    public void follow(@PathVariable int loggedInId, @PathVariable int currentId){
         profileService.followProfile(loggedInId, currentId);
    }
	
	@GetMapping("/getFollowingIds/{loggedInId}")
	List<Integer> getFollowingIds(@PathVariable int loggedInId){
        return profileService.getFollowingIds(loggedInId);
	}

	@PutMapping("/acceptFollowRequest/{to}/{from}")
	public void acceptFollowRequest(@PathVariable int to, @PathVariable int from){
	      profileService.acceptFollowRequest(to, from);
	}

	@PutMapping("/denyFollowRequest/{to}/{from}")
	public void denyFollowRequest(@PathVariable int to, @PathVariable int from){
	      profileService.denyFollowRequest(to, from);
	}
	
	@GetMapping("/getProfile/{username}")
    public UpdateDTO getProfile(@PathVariable String username){
        return profileService.getProfile(username);
    }
	
	@GetMapping("/getExperience/{username}")
    public List<Institution> getExperience(@PathVariable String username){
        return profileService.getExperience(username);
    }
	
	@GetMapping("/getEducation/{username}")
    public List<Institution> getEducation(@PathVariable String username){
        return profileService.getEducation(username);
    }
	
	@GetMapping("/getSkill/{username}")
    public List<Skill> getSkill(@PathVariable String username){
        return profileService.getSkill(username);
    }
	
	@GetMapping("/getInterest/{username}")
    public List<Skill> getInterest(@PathVariable String username){
        return profileService.getInterest(username);
    }

}
