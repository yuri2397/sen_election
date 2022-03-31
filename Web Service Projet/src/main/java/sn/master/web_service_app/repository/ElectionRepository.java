package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Candidate;
import sn.master.web_service_app.entities.Election;
import sn.master.web_service_app.entities.ElectionStatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface ElectionRepository extends JpaRepository<Election, Long> {
    Election findFirstByStatus(ElectionStatus election);

    @Query(value = "SELECT COUNT(\"*\") as voters " +
            "FROM voter " +
            "JOIN election as E " +
            "WHERE E.status = \"NOW\"",
            nativeQuery = true)
    Map<String, BigInteger> findAllVoter();
}
