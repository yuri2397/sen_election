package sn.master.web_service_app.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor @Data

public class FingerPrint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hash;
    private String mask;
}
