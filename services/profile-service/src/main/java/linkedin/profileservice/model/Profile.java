package linkedin.profileservice.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "profiles")
public class Profile {
	
	@Transient
    public static final String SEQUENCE_NAME = "profile_sequence";
	@Id
    private int id;
	
	@Field
    private String biography;

	@Field
    private Boolean isPrivate;
    
    @Field
    private List<Integer> postIds;
    
    @Field
    private int userInfoId;
    
    @Field
    private List<Integer> followingUsersIds;
    
    @Field
    private List<Integer> followersIds;
    
    @Field
    private List<Institution> education;
    
    @Field
    private List<Institution> experience;
    
    @Field
    private List<Skill> interests;
    
    @Field
    private List<Skill> skills;

	public Profile() {
		super();
	}

	public Profile(int idUser) {
		this.userInfoId = idUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public List<Integer> getPostIds() {
		return postIds;
	}

	public void setPostIds(List<Integer> postIds) {
		this.postIds = postIds;
	}

	public int getUserInfo() {
		return userInfoId;
	}

	public void setUserInfo(int userInfo) {
		this.userInfoId = userInfo;
	}

	public List<Integer> getFollowing() {
		return followingUsersIds;
	}

	public void setFollowing(List<Integer> following) {
		this.followingUsersIds = following;
	}

	public List<Integer> getFollowers() {
		return followersIds;
	}

	public void setFollowers(List<Integer> followers) {
		this.followersIds = followers;
	}

	public List<Institution> getEducation() {
		return education;
	}

	public void setEducation(List<Institution> education) {
		this.education = education;
	}

	public List<Institution> getExperience() {
		return experience;
	}

	public void setExperience(List<Institution> experience) {
		this.experience = experience;
	}

	public List<Skill> getInterests() {
		return interests;
	}

	public void setInterests(List<Skill> interests) {
		this.interests = interests;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
}
