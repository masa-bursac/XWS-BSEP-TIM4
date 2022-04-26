package linkedin.pictureservice.service.implementation;

import org.springframework.stereotype.Service;

import linkedin.pictureservice.repository.LinkRepository;
import linkedin.pictureservice.service.ILinkService;

@Service
public class LinkService implements ILinkService{

	private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository){this.linkRepository = linkRepository;}
}
