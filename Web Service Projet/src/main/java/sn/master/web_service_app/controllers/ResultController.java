package sn.master.web_service_app.controllers;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sn.master.web_service_app.services.ResultService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/result")
@NoArgsConstructor
public class ResultController {
    private ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService){
        this.resultService = resultService;
    }

    @GetMapping(path = "/regions/{id}/{status}")
    public Map<String, Object> resultByRegion(@PathVariable Integer id, @PathVariable String status){
        List<Map<String, Object>> candidates = this.resultService.candidateResultByRegion(id,status);

        Map<String, Object > dbResult = this.resultService.forOnRegion(id, status);
        HashMap<String, Object> result = new HashMap<>(dbResult);
        result.put("candidates", candidates);
        return result;
    }

    @GetMapping(path = "/regions-list/{region}/{status}")
    public List<Map<String, Object>> candidateResultByRegion(@PathVariable Integer region, @PathVariable String status){
        return this.resultService.candidateResultByRegion(region, status);
    }

    @GetMapping(path = "/departments/{id}/{status}")
    public Map<String, Object> resultByDepartment(@PathVariable Integer id, @PathVariable String status){

        List<Map<String, Object>> candidates = this.resultService.candidateResultByDepartment(id,status);

        Map<String, Object > dbResult = this.resultService.forOnDepartment(id, status);
        HashMap<String, Object> result = new HashMap<>(dbResult);
        result.put("candidates", candidates);
        return result;

    }

    @GetMapping(path = "/districts/{id}/{status}")
    public Map<String, Object> resultByDistrict(@PathVariable Integer id, @PathVariable String status){
        List<Map<String, Object>> candidates = this.resultService.candidateResultByDistrict(id,status);

        Map<String, Object > dbResult = this.resultService.forOnDistrict(id, status);
        HashMap<String, Object> result = new HashMap<>(dbResult);
        result.put("candidates", candidates);
        return result;
    }

    @GetMapping(path = "/communes/{id}/{status}")
    public Map<String, Object> resultByCommune(@PathVariable Integer id, @PathVariable String status){
        List<Map<String, Object>> candidates = this.resultService.candidateResultByCommune(id,status);

        Map<String, Object > dbResult = this.resultService.forOnCommune(id, status);
        HashMap<String, Object> result = new HashMap<>(dbResult);
        result.put("candidates", candidates);
        return result;
    }

    @GetMapping(path = "/global-result")
    public Map<String, Object> globalResult(){
        return this.resultService.globalResult();
    }

}
