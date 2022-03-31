package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Department;
import sn.master.web_service_app.entities.Region;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(
            value = "select COUNT(\"*\") as voters, dep.name as zone, dep.latitude, dep.longitude " +
                    "FROM department as dep " +
                    "JOIN election as E " +
                    "INNER JOIN district as dist ON dist.department_id = dep.id " +
                    "INNER JOIN commune as com ON dist.id = com.district_id " +
                    "INNER JOIN vote as v ON v.commune_id = com.id " +
                    "WHERE dep.id = :id AND E.id = v.election_id AND E.status = :status",
            nativeQuery = true
    )
    Map<String, Object> resultByDepartment(@Param("id") Integer id, @Param("status") String status);

    @Query(
            value = "SELECT COUNT('*') as voters, V.first_name, V.last_name, V.id as c_id, DEP.name as zone " +
                    "FROM vote " +
                    "JOIN election as ELEC " +
                    "JOIN voter as V ON V.id = vote.candidate_id " +
                    "JOIN commune as COM ON COM.id = vote.commune_id " +
                    "JOIN district as DIS ON DIS.id = COM.district_id " +
                    "JOIN department as DEP ON DEP.id = DIS.department_id " +
                    "WHERE DEP.id = :department and ELEC.status = :status " +
                    "GROUP BY V.first_name, V.last_name, V.id, DEP.name " +
                    "ORDER BY voters DESC",
            nativeQuery = true
    )
    List<Map<String, Object>> candidateResultByDepartment(@Param("department") Integer department, @Param("status") String status);
}
