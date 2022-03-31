package sn.master.web_service_app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sn.master.web_service_app.entities.*;
import sn.master.web_service_app.repository.CandidateRepository;
import sn.master.web_service_app.repository.ElectionRepository;
import sn.master.web_service_app.services.VoteService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/vote")
@NoArgsConstructor
public class VoteController {
    private static final Log log = LogFactory.getLog(VoteController.class);

    private VoteService voteService;
    private CandidateRepository candidateRepository;
    private ElectionRepository electionRepository;

    @Autowired
    public VoteController(VoteService voteService, CandidateRepository candidateRepository, ElectionRepository electionRepository) {
        this.voteService = voteService;
        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
    }

    @PostMapping(path = "init-vote", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, Object> initVote(@RequestBody VoteBodyRequest request) throws ResponseStatusException{
        Map<String, Object> result = new HashMap<>();

        Election e = this.electionRepository.findFirstByStatus(ElectionStatus.NOW);
        result.put("election", e);
        result.put("candidates", this.voteService.candidates(request));
        result.put("voter", this.voteService.getVoter(request.getCne()));
        return result;
    }

    @PostMapping(path = "do-vote", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Vote doVote(@RequestBody DoVoteBodyRequest request) throws ResponseStatusException{
        return this.voteService.doVote(request);
    }
}
