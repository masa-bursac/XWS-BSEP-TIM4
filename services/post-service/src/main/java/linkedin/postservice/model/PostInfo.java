package linkedin.postservice.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class PostInfo {
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
	 
	 private LocalDate date;
	 private String caption;
	 
	 @ElementCollection
	 @CollectionTable(name="Post_Pictures", joinColumns=@JoinColumn(name="PostInfo_ID"))
	 @Column(name="picture")
	 private List<String> picture;
}
