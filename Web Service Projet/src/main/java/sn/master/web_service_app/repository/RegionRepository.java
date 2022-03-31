package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.ElectionStatus;
import sn.master.web_service_app.entities.Region;

import javax.crypto.MacSpi;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query(
            value = "select COUNT(\"*\") as voters, R.name as zone, R.latitude, R.longitude " +
                    "FROM region as R " +
                    "JOIN election as E " +
                    "    JOIN " +
                    "    department as dep " +
                    "    ON dep.region_id = R.id " +
                    "    JOIN " +
                    "    district as dist " +
                    "    ON dist.department_id = dep.id " +
                    "    JOIN " +
                    "    commune as com " +
                    "    ON dist.id = com.district_id " +
                    "    JOIN " +
                    "    vote as v " +
                    "    ON v.commune_id = com.id " +
                    "WHERE R.id = :id AND E.id = v.election_id AND E.status = :status",
            nativeQuery = true
    )
    Map<String, Object> resultByRegion(@Param("id") Integer id, @Param("status") String status);

    @Query(
            value = "SELECT COUNT('*') as voters, V.first_name, V.last_name, V.id as c_id, REG.name as zone " +
                    "FROM vote " +
                    "JOIN election as ELEC " +
                    "JOIN voter as V ON V.id = vote.candidate_id " +
                    "JOIN commune as COM ON COM.id = vote.commune_id " +
                    "JOIN district as DIS ON DIS.id = COM.district_id " +
                    "JOIN department as DEP ON DEP.id = DIS.department_id " +
                    "JOIN region as REG ON REG.id = DEP.region_id " +
                    "WHERE REG.id = :region and ELEC.status = :status " +
                    "GROUP BY V.first_name, V.last_name, V.id, REG.name " +
                    "ORDER BY voters DESC",
            nativeQuery = true
    )
    List<Map<String, Object>> candidateResultByRegion(@Param("region") Integer region, @Param("status") String status);

    @Query(value = "SELECT name FROM region", nativeQuery = true)
    List<String> findNameOnly();

    @Query(
            value = "SELECT COUNT(\"*\") AS voters, voter.first_name " +
                    "FROM region AS R " +
                    "JOIN election AS E " +
                    "JOIN department AS dep ON dep.region_id = R.id " +
                    "JOIN district AS dist ON dist.department_id = dep.id " +
                    "JOIN commune AS com ON dist.id = com.district_id " +
                    "JOIN vote AS v ON v.commune_id = com.id " +
                    "JOIN voter ON v.candidate_id = voter.id " +
                    "WHERE R.name = :region AND E.id = v.election_id AND E.status = \"NOW\" " +
                    "GROUP BY voter.first_name",
            nativeQuery = true
    )
    List<Object> findResultByRegion(@Param("region") String region);
}
