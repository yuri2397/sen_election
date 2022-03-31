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
@NoArgsConstructor @Data
@ToString()
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateBorn;

    @Column(nullable = false)
    private Double size;

    @Column(nullable = false)
    private Long cni;

    @Column(nullable = false)
    private Long cne;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateExpiration;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDelivery;

    @Column(nullable = false)
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private AuthType authType;


    @ManyToOne()
    @JoinColumn(name = "vote_location_id")
    private Commune voteLocation;


    @ManyToOne()
    @JoinColumn(name = "born_address")
    private Commune bornAddress;

    @Transient
    private List<String> fingers;
}
