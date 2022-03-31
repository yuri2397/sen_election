package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Commune;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface CommuneRepository extends JpaRepository<Commune, Long> {

    @Query(
            value = "select COUNT(\"*\") as voters, com.name as zone, com.latitude, com.longitude " +
                    "FROM commune as com " +
                    "JOIN election as E " +
                    "INNER JOIN vote as v ON v.commune_id = com.id " +
                    "WHERE com.id = :id AND E.id = v.election_id AND E.status = :status",
            nativeQuery = true
    )
    Map<String, Object> resultByCommune(@Param("id") Integer id, @Param("status") String status);

    @Query(
            value = "SELECT COUNT('*') as voters, V.first_name, V.last_name, V.id as c_id, COM.name as zone " +
                    "FROM vote " +
                    "JOIN election as ELEC " +
                    "JOIN voter as V ON V.id = vote.candidate_id " +
                    "JOIN commune as COM ON COM.id = vote.commune_id " +
                    "WHERE COM.id = :commune and ELEC.status = :status " +
                    "GROUP BY V.first_name, V.last_name, V.id, COM.name " +
                    "ORDER BY voters DESC",
            nativeQuery = true
    )
    List<Map<String, Object>> candidateResultByCommune(@Param("commune") Integer commune, @Param("status") String status);


}
