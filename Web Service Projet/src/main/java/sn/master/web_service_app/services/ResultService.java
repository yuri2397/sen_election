package sn.master.web_service_app.services;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.master.web_service_app.entities.Candidate;
import sn.master.web_service_app.entities.Election;
import sn.master.web_service_app.entities.ElectionStatus;
import sn.master.web_service_app.repository.*;

import java.math.BigInteger;
import java.util.*;

@Service
@NoArgsConstructor
public class ResultService {
    private RegionRepository regionRepository;
    private DepartmentRepository departmentRepository;
    private DistrictRepository districtRepository;
    private CommuneRepository communeRepository;
    private VoterRepository voterRepository;
    private CandidateRepository candidateRepository;
    private ElectionRepository electionRepository;
    private VoteRepository voteRepository;

    @Autowired
    public ResultService(RegionRepository regionRepository,VoteRepository voteRepository, ElectionRepository electionRepository, DepartmentRepository departmentRepository, DistrictRepository districtRepository, CommuneRepository communeRepository, VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.regionRepository = regionRepository;
        this.departmentRepository = departmentRepository;
        this.districtRepository = districtRepository;
        this.communeRepository = communeRepository;
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
        this.voteRepository = voteRepository;
    }

    public Map<String, Object> forOnRegion(Integer id, String status){
        return this.regionRepository.resultByRegion(id, status);
    }

    public Map<String, Object> forOnDepartment(Integer id, String status) {
        return this.departmentRepository.resultByDepartment(id, status);
    }

    public Map<String, Object> forOnDistrict(Integer id, String status) {
        return this.districtRepository.resultByDistrict(id, status);
    }

    public Map<String, Object> forOnCommune(Integer id, String status) {
        return this.communeRepository.resultByCommune(id, status);
    }

    public List<Map<String, Object>> candidateResultByRegion(Integer region, String status){
        return this.regionRepository.candidateResultByRegion(region, status);
    }

    public List<Map<String, Object>> candidateResultByDepartment(Integer department, String status){
        return this.departmentRepository.candidateResultByDepartment(department, status);
    }

    public List<Map<String, Object>> candidateResultByDistrict(Integer district, String status){
        return this.districtRepository.candidateResultByDistrict(district, status);
    }

    public List<Map<String, Object>> candidateResultByCommune(Integer commune, String status){
        return this.communeRepository.candidateResultByCommune(commune, status);
    }

    public Map<String, Object> globalResult(){
        Map<String, Object> response = new HashMap<>();
        var regions = this.regionRepository.findNameOnly();
        var results = new LinkedList<>();
        var currentElection = this.electionRepository.findFirstByStatus(ElectionStatus.NOW);
        var candidates = this.candidateRepository.findByElectionId(currentElection.getId());
        var legend = new HashMap<>();

        candidates.forEach(candidate -> {
            LinkedList<BigInteger> resultForOnCandidate = new LinkedList<>();
            DataSet data = new DataSet();
            var TotalVote = new Object() {
                BigInteger totalVotes = BigInteger.ZERO;
            };
            this.regionRepository.findAll().forEach(region -> {
                Map<String , BigInteger> res = this.candidateRepository.finalByCandidateInRegionResult(candidate.getId(), region.getId());
                BigInteger votes = res == null ? BigInteger.ZERO : res.get("voters");
                TotalVote.totalVotes = TotalVote.totalVotes.add(votes);
                resultForOnCandidate.add(votes);
            });

            data.setNbVote(TotalVote.totalVotes);
            data.setData(resultForOnCandidate);
            data.setLabel(candidate.getFirstName() + " " + candidate.getLastName());
            data.setYAxisID("y-axis-1");
            data.setBackgroundColor(candidate.getPart().getColorPrimary());
            data.setBorderColor(candidate.getPart().getColorSecondary());
            data.setPointBackgroundColor(candidate.getPart().getColorPrimary());
            data.setPointBorderColor(candidate.getPart().getColorSecondary());
            data.setPointHoverBackgroundColor("#000");
            data.setPointBorderColor("#000");
            results.add(data);
        });

        legend.put("total", this.voteRepository.nbVoteForNow().get("total"));
        legend.put("voters", this.electionRepository.findAllVoter().get("voters"));
        response.put("regions", regions);
        response.put("candidates", results);
        response.put("legend", legend);
        return response;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class DataSet{
        private List<BigInteger> data;
        private String label;
        private String yAxisID;
        private String backgroundColor;
        private String borderColor;
        private String pointBackgroundColor;
        private String pointBorderColor;
        private String pointHoverBackgroundColor;
        private String pointHoverBorderColor;
        private String fill;
        private BigInteger nbVote;
    }
}
