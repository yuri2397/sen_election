package sn.master.web_service_app.entities;

import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultByZone extends Result {
    private Integer voters;
    private Integer subscribed;
    private Collection<ResultByCandidate> candidates;
}
