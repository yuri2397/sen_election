package sn.master.web_service_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor @Data
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String slogan;
    private String colorPrimary;
    private String colorSecondary;

    @JsonIgnore
    @OneToOne()
    private Candidate candidate;
}
