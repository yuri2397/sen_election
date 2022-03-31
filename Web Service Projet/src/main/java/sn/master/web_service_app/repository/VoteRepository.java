package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Vote;

import java.math.BigInteger;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "SELECT COUNT(\"*\") as total " +
            "FROM vote " +
            "JOIN election as E " +
            "ON E.id = vote.election_id " +
            "WHERE E.status = \"NOW\"",
            nativeQuery = true)
    Map<String, BigInteger> nbVoteForNow();

    @Query(
            value = "SELECT vote.id FROM vote " +
                    "JOIN election as E ON E.status = \"NOW\" " +
                    "WHERE mask = :mask AND vote.election_id = E.id", nativeQuery = true
    )
    Map<String, Object> isVoted(@Param("mask") String mask);
}
