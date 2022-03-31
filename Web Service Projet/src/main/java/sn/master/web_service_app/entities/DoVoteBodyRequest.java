package sn.master.web_service_app.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoVoteBodyRequest {
    private Long candidate;
    private Long voter;
    private String finger;
}
