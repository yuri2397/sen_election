package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Candidate;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query(
            value = "SELECT COUNT(*) AS voters" +
                    "FROM region AS R JOIN election AS E " +
                    "JOIN department AS dep ON dep.region_id = R.id " +
                    "JOIN district AS dist ON dist.department_id = dep.id " +
                    "JOIN commune AS com ON dist.id = com.district_id " +
                    "JOIN vote AS v ON v.commune_id = com.id " +
                    "JOIN voter ON v.candidate_id = voter.id " +
                    "WHERE voter.id = :candidate AND E.id = v.election_id AND E.status = \"NOW\" " +
                    "GROUP BY R.id",
            nativeQuery = true
    )
    List<Integer> finalByCandidateResult(@Param("candidate") Long candidate);

    @Query(
            value = "SELECT COUNT(*) AS voters " +
                    "FROM region AS R " +
                    "JOIN election AS E " +
                    "JOIN department AS dep ON dep.region_id = R.id " +
                    "JOIN district AS dist ON dist.department_id = dep.id " +
                    "JOIN commune AS com ON dist.id = com.district_id " +
                    "JOIN vote AS v ON v.commune_id = com.id " +
                    "JOIN voter ON v.candidate_id = voter.id " +
                    "WHERE R.id = :region AND voter.id = :candidate AND E.id = v.election_id AND E.status = \"NOW\"",
            nativeQuery = true
    )
    Map<String, BigInteger> finalByCandidateInRegionResult(@Param("candidate") Long candidate, @Param("region") Long region);


    List<Candidate> findByElectionId(Long electionId);

    @Query(
            value = "SELECT COUNT(*) AS nbVotes " +
                    "FROM vote " +
                    "JOIN election AS E " +
                    "JOIN voter ON v.candidate_id = voter.id " +
                    "WHERE voter.id = :candidate AND E.id = v.election_id AND E.status = \"NOW\"",
            nativeQuery = true
    )
    Map<String, BigInteger> ngVotes(@Param("candidate") Long candidate);
}
