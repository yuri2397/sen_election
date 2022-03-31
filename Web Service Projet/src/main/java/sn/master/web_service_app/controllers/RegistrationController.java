package sn.master.web_service_app.controllers;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sn.master.web_service_app.entities.Voter;
import sn.master.web_service_app.services.RegistrationService;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/registration")
@NoArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * /api/registration/create?fingerPrint=fingerPrint_data
     */
    @PostMapping(path = "create")
    public Voter create(@RequestBody Voter voter) {
        Voter v = this.registrationService.create(voter);
        if (v == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vous etes deja inscrit dans les liste electoral.");
        }
        return v;
    }


}
