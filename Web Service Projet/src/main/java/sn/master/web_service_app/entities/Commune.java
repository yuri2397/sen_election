package sn.master.web_service_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString() @Data
public class Commune extends Zone{
    @JsonIgnore()
    @OneToMany(mappedBy = "voteLocation")
    private Collection<Voter> voters;

    @JsonIgnore()
    @OneToMany(mappedBy = "bornAddress")
    private Collection<Voter> bornHere;

    @JsonIgnore()
    @ManyToOne()
    private District district;
}
