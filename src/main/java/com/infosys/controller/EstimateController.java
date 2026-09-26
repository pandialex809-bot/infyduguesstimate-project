package com.infosys.controller;

import com.infosys.dto.EstimateRequest;
import com.infosys.dto.EstimateResponse;
import com.infosys.service.EstimateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EstimateController {
    private final EstimateService service;
    public EstimateController(EstimateService service) { this.service = service; }

    @GetMapping("/months/{quarter}")
    public List<String> months(@PathVariable String quarter) {
        return service.months(quarter);
    }

    @PostMapping("/estimates")
    @ResponseStatus(HttpStatus.CREATED)
    public List<EstimateResponse> save(@Valid @RequestBody EstimateRequest request) {
        return service.save(request);
    }

    @GetMapping("/estimates/{lob}/{year}/{quarter}")
    public List<EstimateResponse> get(@PathVariable String lob, @PathVariable int year, @PathVariable String quarter) {
        return service.get(lob, year, quarter);
    }

    @GetMapping("/dashboard")
    public List<Map<String, Object>> dashboard() {
        return service.dashboard();
    }
}
