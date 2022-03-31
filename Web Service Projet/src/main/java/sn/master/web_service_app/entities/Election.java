package sn.master.web_service_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor @Data

public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private ElectionStatus status;

    private String description;

    private Time openAt;

    private Time closeAt;

    @JsonIgnore()
    @OneToMany(mappedBy = "election")
    private Collection<Vote> votes;

    @OneToMany(mappedBy = "election")
    private Collection<Candidate> candidates;
}
