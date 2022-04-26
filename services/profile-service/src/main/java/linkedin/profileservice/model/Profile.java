package linkedin.profileservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profile {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    private String biography;
    private Boolean isPrivate;
    
    @ElementCollection
    @CollectionTable(name="Profile_Posts", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="post")
    private List<Integer> postIds;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInfo userInfo;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Profile_Following",
            joinColumns = { @JoinColumn(name = "Profile_ID") },
            inverseJoinColumns = { @JoinColumn(name = "Following_ID") })
    private List<Profile> following;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Profile_Followers",
            joinColumns = { @JoinColumn(name = "Profile_ID") },
            inverseJoinColumns = { @JoinColumn(name = "Followers_ID") })
    private List<Profile> followers;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Profile_Education",
            joinColumns = { @JoinColumn(name = "Profile_ID") },
            inverseJoinColumns = { @JoinColumn(name = "Education_ID") })
    private List<Institution> education;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Profile_Experience",
            joinColumns = { @JoinColumn(name = "Profile_ID") },
            inverseJoinColumns = { @JoinColumn(name = "Experience_ID") })
    private List<Institution> experience;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Profile_Interests",
            joinColumns = { @JoinColumn(name = "Profile_ID") },
            inverseJoinColumns = { @JoinColumn(name = "Interests_ID") })
    private List<Skill> interests;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Profile_Skills",
            joinColumns = { @JoinColumn(name = "Profile_ID") },
            inverseJoinColumns = { @JoinColumn(name = "Skills_ID") })
    private List<Skill> skills;
}
