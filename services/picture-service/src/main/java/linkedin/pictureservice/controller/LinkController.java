package linkedin.pictureservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.pictureservice.service.ILinkService;

@RestController
@RequestMapping("/link")
public class LinkController {

	private final ILinkService linkService;

    public LinkController(ILinkService linkService){this.linkService = linkService;}
}
