package sn.master.web_service_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import sn.master.web_service_app.entities.*;
import sn.master.web_service_app.repository.CandidateRepository;
import sn.master.web_service_app.repository.CommuneRepository;
import sn.master.web_service_app.repository.ElectionRepository;
import sn.master.web_service_app.repository.VoterRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class WebServiceProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceProjetApplication.class, args);
    }


    @Bean
    CommandLineRunner start(VoterRepository voterRepository, RepositoryRestConfiguration restConfiguration, ElectionRepository electionRepository, CandidateRepository candidateRepository, CommuneRepository communeRepository) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return args -> {

            restConfiguration.exposeIdsFor(Region.class);
            restConfiguration.exposeIdsFor(Department.class);
            restConfiguration.exposeIdsFor(District.class);
            restConfiguration.exposeIdsFor(Commune.class);

//            Commune c = communeRepository.findById(14L).get();
//            Commune c1 = communeRepository.findById(12L).get();
//            Election e = electionRepository.findById(1L).get();

            /*

                voterRepository.save(new Voter(
                        null, "Makha", "Seck", simpleDateFormat.parse("1995/2/15"), 1.88, 12345678915L, 12345678915L, new Date(), new Date(), "makka-seck.jpg", Sex.MAN, AuthType.NATUREL, c, c
                ));
            voterRepository.save(new Voter(
                    null, "Ousseynou", "Diagne", simpleDateFormat.parse("1997/10/15"), 1.75, 12345678916L, 12345678916L, new Date(), new Date(), "makka-seck.jpg", Sex.MAN, AuthType.NATUREL, c1, c
            ));

            voterRepository.save(new Voter(
                    null, "Babacar", "Ndiaye", simpleDateFormat.parse("1995/2/15"), 1.88, 12345678919L, 12345678919L, new Date(), new Date(), "makka-seck.jpg", Sex.MAN, AuthType.NATUREL, c, c
            ));

            voterRepository.save(new Voter(
                    null, "Papa Daouda", "Ndiaye", simpleDateFormat.parse("1998/24/10"), 1.73, 12345678918L, 12345678919L, new Date(), new Date(), "avatar.png", Sex.MAN, AuthType.NATUREL, c1, c1
            ));*/
        };
    }

}
