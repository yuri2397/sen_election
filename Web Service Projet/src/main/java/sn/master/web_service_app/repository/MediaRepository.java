package sn.master.web_service_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sn.master.web_service_app.entities.Media;
import sn.master.web_service_app.entities.Voter;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "data")
public interface MediaRepository extends JpaRepository<Media, Long> {


}
