package sn.master.web_service_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString @Data

public class Department extends Zone{

    @JsonIgnore()
    @ManyToOne()
    private Region region;

    @OneToMany(mappedBy = "department")
    private Collection<District> districts;
}
