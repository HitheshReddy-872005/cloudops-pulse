package com.cloudops.pulse;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthCheckController {

    private final HealthCheckService healthCheckService;

    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/check")
    public Map<String, Object> checkHealth(@RequestParam String url) {
        return healthCheckService.pingTarget(url);
    }
}
