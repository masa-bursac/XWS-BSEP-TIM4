package linkedin.pictureservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.pictureservice.service.implementation.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

	private final ImageService imageService;

    public ImageController(ImageService imageService){this.imageService = imageService;}
}
