package linkedin.requestservice.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import linkedin.requestservice.client.ProfileClient;
import linkedin.requestservice.dto.FollowRequestDTO;
import linkedin.requestservice.model.FollowRequest;
import linkedin.requestservice.repository.FollowRequestRepository;
import linkedin.requestservice.service.IFollowRequestService;

@Service
public class FollowRequestService implements IFollowRequestService {
	
	private final FollowRequestRepository followRequestRepository;
    private final ProfileClient profileClient;
    static SequenceGeneratorService sequenceGeneratorService;

    public FollowRequestService(FollowRequestRepository followRequestRepository, ProfileClient profileClient, SequenceGeneratorService sequenceGeneratorService) {
        this.followRequestRepository = followRequestRepository;
        this.profileClient = profileClient;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public FollowRequest newRequest(FollowRequest request) {
    	request.setId((int) sequenceGeneratorService.generateSequence(FollowRequest.SEQUENCE_NAME));
        return followRequestRepository.save(request);
    }
    
    @Override
    public List<FollowRequestDTO> getAllForProfile(int loggedIn) {
        List<FollowRequest> followRequests = findAllByToProfileId(loggedIn);
        List<FollowRequestDTO> usernames = new ArrayList<>();
        for(FollowRequest followRequest : followRequests)
        {
            String username = profileClient.getUsername(followRequest.getFromProfileId());
            usernames.add(new FollowRequestDTO(username,followRequest.getFromProfileId()));
        }
        return usernames;
    }
    
    public List<FollowRequest> findAllByToProfileId(int loggedIn){
    	List<FollowRequest> followRequests = followRequestRepository.findAll();
    	List<FollowRequest> returnRequests = new ArrayList<>();
    	for(int i=0;i<followRequests.size(); i++) {
    		if(followRequests.get(i).getToProfileId()==loggedIn) {
    			returnRequests.add(followRequests.get(i));
    		}
    	}
    	return returnRequests;
    }
    
    @Override
    public void delete(int to, int from) {
        FollowRequest followRequest = findOneByToAndFrom(to, from);
        followRequestRepository.delete(followRequest);
        
    }
    
    public FollowRequest findOneByToAndFrom(int to, int from){
    	List<FollowRequest> followRequests = followRequestRepository.findAll();
    	FollowRequest returnRequest = new FollowRequest();
    	for(int i=0;i<followRequests.size(); i++) {
    		if((followRequests.get(i).getToProfileId()==to)&&(followRequests.get(i).getFromProfileId()==from) ) {
    			returnRequest = followRequests.get(i);
    		}
    	}
    	return returnRequest;
    }

}
