package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.District;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface DistrictRepository extends JpaRepository<District, Long> {
    @Query(
            value = "select COUNT(\"*\") as voters, dist.name as zone, dist.latitude, dist.longitude " +
                    "FROM district as dist " +
                    "JOIN election as E " +
                    "JOIN commune as com ON dist.id = com.district_id " +
                    "JOIN vote as v ON v.commune_id = com.id " +
                    "WHERE dist.id = :id AND E.id = v.election_id AND E.status = :status",
            nativeQuery = true
    )
    Map<String, Object> resultByDistrict(@Param("id") Integer id, @Param("status") String status);

    @Query(
            value = "SELECT COUNT('*') as voters, V.first_name, V.last_name, V.id as c_id, DIS.name as zone " +
                    "FROM vote " +
                    "JOIN election as ELEC " +
                    "JOIN voter as V ON V.id = vote.candidate_id " +
                    "JOIN commune as COM ON COM.id = vote.commune_id " +
                    "JOIN district as DIS ON DIS.id = COM.district_id " +
                    "WHERE DIS.id = :district and ELEC.status = :status " +
                    "GROUP BY V.first_name, V.last_name, V.id, DIS.name " +
                    "ORDER BY voters DESC",
            nativeQuery = true
    )
    List<Map<String, Object>> candidateResultByDistrict(@Param("district") Integer district, @Param("status") String status);
}
