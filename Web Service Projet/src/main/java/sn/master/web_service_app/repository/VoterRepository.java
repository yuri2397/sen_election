package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Voter;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface VoterRepository extends JpaRepository<Voter, Long> {

    Voter findFirstByCne(Long cne);

    Voter findFirstByCni(Long cni);

}
