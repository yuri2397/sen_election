package sn.master.web_service_app.services;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sn.master.web_service_app.entities.*;
import sn.master.web_service_app.repository.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@NoArgsConstructor
public class VoteService {
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);

    private AuthService authService;
    private ElectionRepository electionRepository;
    private CommuneRepository communeRepository;
    private RegionRepository regionRepository;
    private DepartmentRepository departmentRepository;
    private DistrictRepository districtRepository;
    private VoterRepository voterRepository;
    private CandidateRepository candidateRepository;
    private VoteRepository voteRepository;

    @Autowired
    public VoteService(CandidateRepository candidateRepository,VoteRepository voteRepository, VoterRepository voterRepository,AuthService authService,CommuneRepository communeRepository, ElectionRepository electionRepository){
        this.electionRepository = electionRepository;
        this.authService = authService;
        this.communeRepository = communeRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
    }

    public Vote doVote(DoVoteBodyRequest request){
        Election currentElection = this.electionRepository.findFirstByStatus(ElectionStatus.NOW);
        try{
            var voter = this.voterRepository.findById(request.getVoter()).get();
            var candidate = this.candidateRepository.findById(request.getCandidate()).get();
            if(this.isAlreadyVoted(request.getFinger())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Vous avez déjà voté.");
            }
            String mask = this.authService.getMask(request.getFinger());

            return this.applyVote(mask, currentElection,voter, candidate);
        }
        catch (NoSuchElementException e){
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Vérifier les informations fournies");
        }
    }

    private Vote applyVote(String mask,Election election, Voter voter, Candidate candidate){
        return this.voteRepository.save(new Vote(null, mask, LocalDateTime.now(),election, candidate,voter.getVoteLocation() ));
    }

    private boolean isAlreadyVoted(String finger) {
        String mask = this.authService.getMask(finger);
        if(mask == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Erreur d'authentification.");
        logger.info(mask);
        Map<String, Object> result = this.voteRepository.isVoted(mask);

        return result.size() != 0;
    }

    public Voter getVoter(Long cne){
        return this.authService.isVoter(cne);
    }

    public Collection<Candidate> candidates(VoteBodyRequest request) throws ResponseStatusException{
        Voter v = this.authService.isVoter(request.getCne());
        if(v != null)
            return this.electionRepository.findFirstByStatus(ElectionStatus.NOW).getCandidates();
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vous n'etes pas dans la liste.");
    }

    public Commune voterLocation(Voter voter) {
        return voter.getVoteLocation();
    }
}
