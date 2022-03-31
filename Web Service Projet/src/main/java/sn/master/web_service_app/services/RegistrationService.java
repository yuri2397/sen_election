package sn.master.web_service_app.services;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sn.master.web_service_app.entities.AuthType;
import sn.master.web_service_app.entities.FingerPrint;
import sn.master.web_service_app.entities.Voter;
import sn.master.web_service_app.repository.FingerPrintRepository;
import sn.master.web_service_app.repository.VoterRepository;

import java.util.*;

@Service
public class RegistrationService {
    private VoterRepository voterRepository;
    private FingerPrintRepository fingerPrintRepository;

    @Autowired
    public RegistrationService(VoterRepository voterRepository, FingerPrintRepository fingerPrintRepository){
        this.voterRepository = voterRepository;
        this.fingerPrintRepository = fingerPrintRepository;
    }

    public Voter create(Voter voter){
        if(this.validate(voter)){
            var isExist = this.voterRepository.findFirstByCni(voter.getCni());
            if(isExist == null){
                var c = Calendar.getInstance();
                c.add(Calendar.YEAR, 10);
                voter.setDateDelivery(new Date());
                voter.setDateExpiration(c.getTime());
                voter.setCne(Calendar.getInstance().toInstant().toEpochMilli());
                List<String> fingers = new ArrayList<>();

                if(voter.getAuthType() == AuthType.ARTIFICIAL){
                    fingers.add(voter.getCni().toString());
                }
                else{
                    fingers.addAll(voter.getFingers());
                }
                this.setFingers(fingers);
                return this.voterRepository.save(voter);
            }
            return null;
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merci de donner les dix empreintes de l'élécteur.");
        }
    }

    private String setFingers(List<String> fingers){
        var Mask = new Object() {
            String mask = "";
        };
        fingers.forEach((f) -> Mask.mask += f);
        Mask.mask = BCrypt.hashpw(Mask.mask, BCrypt.gensalt());
        fingers.forEach((finger) -> this.fingerPrintRepository.save(new FingerPrint(null,BCrypt.hashpw(finger, BCrypt.gensalt()), Mask.mask)));
        return Mask.mask;
    }



    private boolean validate(Voter voter){
        if(voter.getFingers().size() < 1) return false;
        return true;
    }
}
