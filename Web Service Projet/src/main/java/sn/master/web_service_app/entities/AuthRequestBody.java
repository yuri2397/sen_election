package sn.master.web_service_app.entities;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Data
public class AuthRequestBody {
    private Long cni;
    private Long cne;
}
