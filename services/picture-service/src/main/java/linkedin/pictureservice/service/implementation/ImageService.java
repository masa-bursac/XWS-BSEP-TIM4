package linkedin.pictureservice.service.implementation;

import org.springframework.stereotype.Service;

import linkedin.pictureservice.repository.ImageRepository;

@Service
public class ImageService {

	private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository){this.imageRepository = imageRepository;}
}
