package linkedin.pictureservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.pictureservice.dto.ImageDTO;
import linkedin.pictureservice.service.implementation.ImageService;

@RestController
@RequestMapping("/picture")
public class ImageController {

	private final ImageService imageService;

    public ImageController(ImageService imageService){this.imageService = imageService;}
    
    @PostMapping("/upload")
    Integer uploadImage(@RequestBody ImageDTO imageDTO) throws Exception {
        return imageService.save(imageDTO.getContent(),imageDTO.getName(), imageDTO.isImage());
    }
}
