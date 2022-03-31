package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Candidate;
import sn.master.web_service_app.entities.FingerPrint;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface FingerPrintRepository extends JpaRepository<FingerPrint, Long> {
    FingerPrint findByHash(String hash);
}
