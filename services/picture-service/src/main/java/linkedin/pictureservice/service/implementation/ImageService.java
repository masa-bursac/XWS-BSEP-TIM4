package linkedin.pictureservice.service.implementation;

import org.springframework.stereotype.Service;

import linkedin.pictureservice.model.Image;
import linkedin.pictureservice.model.Link;
import linkedin.pictureservice.repository.ImageRepository;
import linkedin.pictureservice.repository.LinkRepository;

@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final LinkRepository linkRepository;
	static SequenceGeneratorService sequenceGeneratorService;

    public ImageService(ImageRepository imageRepository, SequenceGeneratorService sequenceGeneratorService, LinkRepository linkRepository)
    {this.imageRepository = imageRepository;
    this.sequenceGeneratorService = sequenceGeneratorService;
    this.linkRepository = linkRepository;
    }

	public Integer save(byte[] content, String name, boolean image) {
		if(image) 
		{
			Image newImage = new Image();
			newImage.setContent(content);
			newImage.setName(name);
			newImage.setImage(image);
			newImage.setId((int) sequenceGeneratorService.generateSequence(Image.SEQUENCE_NAME));
			imageRepository.save(newImage);
			return newImage.getId();
		}else 
		{
			Link newLink = new Link();
			newLink.setPath(content);
			newLink.setId((int) sequenceGeneratorService.generateSequence(Link.SEQUENCE_NAME));
			linkRepository.save(newLink);
			return newLink.getId();
		}
	}
}
