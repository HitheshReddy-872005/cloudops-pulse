package com.cloudops.pulse;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class HealthCheckService {

    public Map<String, Object> pingTarget(String targetUrl) {
        Map<String, Object> response = new HashMap<>();
        long startTime = System.currentTimeMillis();

        try {
            if (!targetUrl.startsWith("http://") && !targetUrl.startsWith("https://")) {
                targetUrl = "https://" + targetUrl;
            }

            URL url = URI.create(targetUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            
            int statusCode = conn.getResponseCode();
            long latency = System.currentTimeMillis() - startTime;

            response.put("target", targetUrl);
            response.put("statusCode", statusCode);
            response.put("latencyMs", latency);
            response.put("status", (statusCode >= 200 && statusCode < 400) ? "UP" : "DEGRADED");
        } catch (IOException e) {
            response.put("target", targetUrl);
            response.put("statusCode", 503);
            response.put("latencyMs", System.currentTimeMillis() - startTime);
            response.put("status", "DOWN");
            response.put("error", e.getMessage());
        }
        return response;
    }
}
