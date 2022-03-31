package sn.master.web_service_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString @Data

public class District extends Zone{
    @OneToMany(mappedBy = "district")
    private Collection<Commune> communes;

    @JsonIgnore()
    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;

}
