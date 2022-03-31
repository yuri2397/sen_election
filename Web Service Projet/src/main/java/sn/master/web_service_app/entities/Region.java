package sn.master.web_service_app.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString @Data
public class Region extends Zone {
    @OneToMany(mappedBy = "region")
    private Collection<Department> departments;
}
