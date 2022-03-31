package sn.master.web_service_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor @Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String mask;

    private LocalDateTime time;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @JsonIgnore
    @ManyToOne()
    private Candidate candidate;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "commune_id")
    private Commune commune;

}
