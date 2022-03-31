package sn.master.web_service_app.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultByCandidate extends  Result{
    private Integer nbVoter;
}
