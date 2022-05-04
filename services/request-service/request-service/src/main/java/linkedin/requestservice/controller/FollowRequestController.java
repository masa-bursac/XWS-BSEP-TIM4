package linkedin.requestservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import linkedin.requestservice.dto.FollowRequestDTO;
import linkedin.requestservice.model.FollowRequest;
import linkedin.requestservice.service.IFollowRequestService;

@RestController
@RequestMapping("request")
public class FollowRequestController {

	private final IFollowRequestService followRequestService;

    public FollowRequestController(IFollowRequestService followRequestService) {
        this.followRequestService = followRequestService;
    }

    @PostMapping("/newRequest")
    public FollowRequest newRequest(@RequestBody FollowRequest followRequest) {
        return followRequestService.newRequest(followRequest);
    }
    
    @GetMapping("/getAllForProfile/{loggedInId}")
    public List<FollowRequestDTO> getAllForProfile(@PathVariable int loggedInId)
    {
        return followRequestService.getAllForProfile(loggedInId);
    }
    
    @PutMapping("/delete/{to}/{from}")
    public void delete(@PathVariable int to, @PathVariable int from)
    {
         followRequestService.delete(to, from);
    }
}
