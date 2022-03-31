package sn.master.web_service_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Data
public class Candidate extends Voter{

    public Candidate(Long id, String firstName,Commune bornAddress, String lastName, Date dateBorn, Double size, Long cni, Long cne, Date dateExpiration, Date dateDelivery, String avatar, Sex sex, AuthType authType, Commune voteLocation, Election election, List<String> fingers) {
        super(id, firstName, lastName, dateBorn, size, cni, cne, dateExpiration, dateDelivery, avatar, sex, authType, voteLocation, bornAddress, fingers);
        this.election = election;
    }

    @OneToMany(mappedBy = "ownId")
    private Collection<Media> medias;

    @OneToOne(fetch = FetchType.EAGER)
    private Part part;

    @JsonIgnore()
    @OneToMany(mappedBy = "candidate")
    private Collection<Vote> votes;


    @JsonIgnore()
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    private Election election;
}
