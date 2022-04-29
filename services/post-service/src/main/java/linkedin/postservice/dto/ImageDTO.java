package linkedin.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
	
	private String name;
    private byte[] content;
    private boolean image;	//umesto linka
    
}
