package sn.master.web_service_app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import sn.master.web_service_app.entities.Candidate;
import sn.master.web_service_app.entities.FingerPrint;
import sn.master.web_service_app.entities.Voter;
import sn.master.web_service_app.repository.CandidateRepository;
import sn.master.web_service_app.repository.FingerPrintRepository;
import sn.master.web_service_app.repository.VoterRepository;

import java.util.List;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private VoterRepository voterRepository;
    private CandidateRepository candidateRepository;
    private FingerPrintRepository fingerPrintRepository;

    @Autowired
    public AuthService(VoterRepository voterRepository,FingerPrintRepository fingerPrintRepository , CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.fingerPrintRepository = fingerPrintRepository;
    }

    public Voter isVoter(Long cne){
        return this.voterRepository.findFirstByCne(cne);
    }

    public String getMask(String finger){
        List<FingerPrint> fingerPrint = this.fingerPrintRepository.findAll();
        for(var f : fingerPrint){
            if(BCrypt.checkpw(finger, f.getHash())){
                return f.getMask();
            }
        }
        return null;
    }
}
